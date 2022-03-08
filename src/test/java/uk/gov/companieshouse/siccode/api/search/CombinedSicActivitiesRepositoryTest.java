package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.companieshouse.siccode.api.groups.TestType;

/**
 *  Do integration testing on an embedded Mongo Database (note that this api will be tested
 *  later in Karate tests against a "real" Mongo database).
 */
@Tag(TestType.INTEGRATION)
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

        // We need to create a text index for this collection so that a Text Search works (else get the error - failed with error code 27 and error message ‘text index required for $text query")
        mongoTemplate.indexOps("combined_sic_activities").ensureIndex(TextIndexDefinition.builder().onFields("activity_description_search_field").build());
    }

    @AfterEach
    public void afterEach() {
        mongoTemplate.dropCollection(CombinedSicActivitiesStorageModel.class);
    }

    @Test
    @DisplayName("Correct items are returns in the correct order (since each result item has differing number of matches)")
    void textSearchThreeItemsAnyMatchVerifySorting() {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("Bean Growing Organic").getTextCriteriaMatchAny());

        assertEquals(3, results.size());

        // ordered assertion with contains and number of matches are 3, 2, 1
        assertThat(results, contains( BEAN_GROWING_ORGANIC, BEAN_GROWING, BARLEY_GROWING));
    }

    @Test
    @DisplayName("Correct two items for a search with one search word")
    void textSearchOneItemAnyMatch() {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("manufacture").getTextCriteriaMatchAny());
        
        assertEquals(2, results.size());

        assertThat(results, containsInAnyOrder( BARLEY_MALTING, BUS_MANUFACTURE ));
    }

    @Test
    @DisplayName("Correct five items for a search with two search word")
    void textSearchTwoItemAnyMatch() {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("Barley growing").getTextCriteriaMatchAny());
        
        assertEquals(5, results.size());

        assertThat(results, containsInAnyOrder( BARLEY_FARMING, BARLEY_GROWING, BARLEY_MALTING, BEAN_GROWING, BEAN_GROWING_ORGANIC));
    }

    @Test
    @DisplayName("Correct item for a `phase match` search with a phase of two words")
    void textSearchTwoItemPhraseMatch () {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("barley farming").getTextCriteriaMatchPhrase());
        
        assertEquals(1, results.size());

        assertThat(results, containsInAnyOrder(BARLEY_FARMING));
    }

}
