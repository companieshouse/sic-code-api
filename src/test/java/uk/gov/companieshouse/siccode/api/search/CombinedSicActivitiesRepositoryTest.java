package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
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
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.TextCriteria;
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
	"barley farming", "Growing of cereals except rice, leguminous crops and oil seeds", false);

	private final static CombinedSicActivitiesStorageModel BARLEY_GROWING = new CombinedSicActivitiesStorageModel("15", "01110", "Barley growing",
	"barley growing", "Growing of cereals except rice, leguminous crops and oil seeds", false);

	private final static CombinedSicActivitiesStorageModel BEAN_GROWING_ORGANIC = new CombinedSicActivitiesStorageModel("17", "01110", "Bean growing organic",
	"bean growing organic", "Growing of cereals except rice, leguminous crops and oil seeds and manufacture", false);

	private final static CombinedSicActivitiesStorageModel BEAN_GROWING = new CombinedSicActivitiesStorageModel("18", "01110", "Bean growing",
	"bean growing", "Growing of cereals except rice, leguminous crops and oil seeds", false);

	private final static CombinedSicActivitiesStorageModel ARMOURED_CAR_SERVICES = new CombinedSicActivitiesStorageModel("20", "80100", "Armoured car services",
	"armoured car services", "Private security activities", false);

	private final static CombinedSicActivitiesStorageModel BARLEY_MALTING = new CombinedSicActivitiesStorageModel("30", "11060", "Barley malting (manufacture)",
	"barley malting manufacture", "Manufacture of malt", false);

	private final static CombinedSicActivitiesStorageModel BUS_MANUFACTURE = new CombinedSicActivitiesStorageModel("40", "29201", "Body for bus (manufacture)",
	"body for bus manufacture", "Manufacture of bodies (coachwork) for motor vehicles (except caravans)", false);

	@BeforeEach
	public void beforeEach() {
	
		var combinedSicActivities = Arrays.asList(BARLEY_FARMING, BARLEY_GROWING, BEAN_GROWING, BEAN_GROWING_ORGANIC, ARMOURED_CAR_SERVICES, BARLEY_MALTING, BUS_MANUFACTURE);
		combinedSicActivitiesRepository.saveAll(combinedSicActivities);	
	}

	@AfterEach
	public void afterEach() {
		mongoTemplate.dropCollection(CombinedSicActivitiesStorageModel.class);
	}


	@Test
	void textSearchThreeItemsOrMatchVerifySorting() {

		// Is this correct - do I need to specify the one text field? Also this needs #spring.data.mongodb.auto-index-creation=true
		mongoTemplate.indexOps("combined_sic_activities").ensureIndex(TextIndexDefinition.builder().onFields("activity_description_search_field").build());

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("bean growing organic");

		var results = combinedSicActivitiesRepository.findAllByOrderByScore(criteria);

		var sicCode1 = results.get(0).getSicCode();
		System.out.println("Sic code " + sicCode1);
		
		assertEquals(3, results.size());

		assertThat(results, contains( BEAN_GROWING_ORGANIC, BEAN_GROWING, BARLEY_GROWING));
	}

	/*
	@Test
	void textSearchTwoItemOrMatch() {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("barley growing");

		var results = combinedSicActivitiesRepository.findAllByOrderByScore(criteria);
		
		assertEquals(5, results.size());

		assertThat(results, containsInAnyOrder( BARLEY_FARMING, BARLEY_GROWING, BARLEY_MALTING, BEAN_GROWING, BEAN_GROWING_ORGANIC));
	}

	@Test
	void textSearchOneItemWithinBracketsOrMatch() {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("manufacture");

		var results = combinedSicActivitiesRepository.findAllByOrderByScore(criteria);
		
		assertEquals(2, results.size());

		assertThat(results, containsInAnyOrder( BARLEY_MALTING, BUS_MANUFACTURE ));
	}

	@Test
	void textSearchTwoItemAndMatch () {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase("barley farming");

		var results = combinedSicActivitiesRepository.findAllByOrderByScore(criteria);
		
		assertEquals(1, results.size());

		assertThat(results, containsInAnyOrder(BARLEY_FARMING));
	}
*/

}
