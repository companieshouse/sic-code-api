package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

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

	private final static CombinedSicActivitiesStorageModel BARLEY_FARMING = new CombinedSicActivitiesStorageModel("10", "01110", "Barley Farming",
	"barley farming", "Growing of cereals (except rice), leguminous crops and oil seeds", false);

	private final static CombinedSicActivitiesStorageModel BARLEY_GROWING = new CombinedSicActivitiesStorageModel("15", "01110", "Barley growing",
	"barley growing", "Growing of cereals (except rice), leguminous crops and oil seeds", false);

	private final static CombinedSicActivitiesStorageModel BEAN_GROWING = new CombinedSicActivitiesStorageModel("17", "01110", "Bean growing",
	"bean growing", "Growing of cereals (except rice), leguminous crops and oil seeds", false);

	private final static CombinedSicActivitiesStorageModel ARMOURED_CAR_SERVICES = new CombinedSicActivitiesStorageModel("20", "80100", "Armoured car services",
	"armoured car services", "Private security activities", false);

	private final static CombinedSicActivitiesStorageModel BARLEY_MALTING = new CombinedSicActivitiesStorageModel("30", "11060", "Barley malting (manufacture)",
	"barley malting (manufacture)", "Manufacture of malt", false);

	private final static CombinedSicActivitiesStorageModel BUS_MANUFACTURE = new CombinedSicActivitiesStorageModel("40", "29201", "Body for bus (manufacture)",
	"body for bus (manufacture)", "Manufacture of bodies (coachwork) for motor vehicles (except caravans)", false);

	@BeforeEach
	public void beforeEach() {
	
		var combinedSicActivities = Arrays.asList(BARLEY_FARMING, BARLEY_GROWING, BEAN_GROWING, ARMOURED_CAR_SERVICES, BARLEY_MALTING, BUS_MANUFACTURE);
		combinedSicActivitiesRepository.saveAll(combinedSicActivities);	
	}

	@AfterEach
	public void afterEach() {
		mongoTemplate.dropCollection(CombinedSicActivitiesStorageModel.class);
	}

	@Test
	void searchOneItemOrMatch() {
		var results = combinedSicActivitiesRepository.findByActivityDescriptionLowerCaseRegex(
			new SearchRegularExpression("barley", false).getRegularExpression());
		
		assertEquals(3, results.size());

		assertThat(results, containsInAnyOrder( BARLEY_FARMING, BARLEY_GROWING, BARLEY_MALTING ));
	}

	@Test
	void searchTwoItemOrMatch () {
		var results = combinedSicActivitiesRepository.findByActivityDescriptionLowerCaseRegex(
			new SearchRegularExpression("car bus", false).getRegularExpression());
		
		assertEquals(2, results.size());

		assertThat(results, containsInAnyOrder(ARMOURED_CAR_SERVICES, BUS_MANUFACTURE));
	}

	@Test
	void searchTwoItemAndMatch () {
		var results = combinedSicActivitiesRepository.findByActivityDescriptionLowerCaseRegex(
			new SearchRegularExpression("barley farming", true).getRegularExpression());
		
		assertEquals(1, results.size());

		assertThat(results, containsInAnyOrder(BARLEY_FARMING));
	}


}
