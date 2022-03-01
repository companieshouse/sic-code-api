package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.companieshouse.ocr.api.groups.TestType;



@Tag(TestType.UNIT)
@DataMongoTest
@ExtendWith(SpringExtension.class)
class CombinedSicActivitiesRepositoryTest {

	@Autowired
    private CombinedSicActivitiesRepository combinedSicActivitiesRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeEach
	public void beforeEach() {

		var barleyFarming = new CombinedSicActivitiesStorageModel("10", "01110", "Barley Farming",
		"barley farming", "Growing of cereals (except rice), leguminous crops and oil seeds", false);

		var barleyGrowing = new CombinedSicActivitiesStorageModel("15", "01110", "Barley growing",
		"barley growing", "Growing of cereals (except rice), leguminous crops and oil seeds", false);

		var beanGrowing = new CombinedSicActivitiesStorageModel("17", "01110", "Bean growing",
		"bean growing", "Growing of cereals (except rice), leguminous crops and oil seeds", false);

		var armouredCarServices = new CombinedSicActivitiesStorageModel("20", "80100", "Armoured car services",
		"armoured car services", "Private security activities", false);

		var barleyMalting = new CombinedSicActivitiesStorageModel("30", "11060", "Barley malting (manufacture)",
		"barley malting (manufacture)", "Manufacture of malt", false);

		var busManufacture = new CombinedSicActivitiesStorageModel("40", "29201", "Body for bus (manufacture)",
		"body for bus (manufacture)", "Manufacture of bodies (coachwork) for motor vehicles (except caravans)", false);
		
		var combinedSicActivities = Arrays.asList(barleyFarming, barleyGrowing, beanGrowing, armouredCarServices, barleyMalting, busManufacture);
		combinedSicActivitiesRepository.saveAll(combinedSicActivities);
	
	}

	@AfterEach
	public void afterEach() {
		mongoTemplate.dropCollection(CombinedSicActivitiesStorageModel.class);
	}

	@Test
	void searchOneItemOrMatch() {
		var results = combinedSicActivitiesRepository.findByActivityDescriptionLowerCaseRegex("barley");
		
		System.out.println("Number of results [" + results.size() + "]");
		assertEquals(3, results.size());

		List<String> resultSicCodes = results.stream()
		.map(CombinedSicActivitiesStorageModel::getSicCode)
		.collect(Collectors.toList());

		assertThat(resultSicCodes, containsInAnyOrder("01110", "01110", "11060"));

	}

	@Test
	void searchTwoItemOrMatch () {
		var results = combinedSicActivitiesRepository.findByActivityDescriptionLowerCaseRegex("car|bus");
		
		System.out.println("Number of results [" + results.size() + "]");
		assertEquals(2, results.size());

		List<String> resultSicCodes = results.stream()
		.map(CombinedSicActivitiesStorageModel::getSicCode)
		.collect(Collectors.toList());

		assertThat(resultSicCodes, containsInAnyOrder("80100", "29201"));
	}

}
