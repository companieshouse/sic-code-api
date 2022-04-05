package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

import uk.gov.companieshouse.siccode.api.groups.TestType;

/**
 *  Do integration testing on an embedded Mongo Database (note that this api will be tested
 *  later in Karate tests against a "real" Mongo database).
 */
@Tag(TestType.INTEGRATION)
@DataMongoTest
class CombinedSicActivitiesRepositoryTest {

    @Autowired
    private CombinedSicActivitiesRepository combinedSicActivitiesRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void beforeEach() {
    
        var combinedSicActivities = new ArrayList<CombinedSicActivitiesStorageModel>();
        combinedSicActivities.add(SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL);
        combinedSicActivities.add(SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL);
        combinedSicActivities.add(SicCodeTestData.BEAN_GROWING_STORAGE_MODEL);
        combinedSicActivities.add(SicCodeTestData.BEAN_GROWING_ORGANIC_STORAGE_MODEL);
        combinedSicActivities.add(SicCodeTestData.ARMOURED_CAR_SERVICES_STORAGE_MODEL);
        combinedSicActivities.add(SicCodeTestData.BARLEY_MALTING_STORAGE_MODEL);
        combinedSicActivities.add(SicCodeTestData.BUS_MANUFACTURE_STORAGE_MODEL);
        
        combinedSicActivitiesRepository.saveAll(combinedSicActivities);	

        // We need to create a text index for this collection so that a Text Search works (else get the error - failed with error code 27 and error message ‘text index required for $text query")
        mongoTemplate.indexOps("combined_sic_activities").ensureIndex(TextIndexDefinition.builder().onFields("activity_description_search_field","sic_code","sic_description").build());
    }

    @AfterEach
    public void afterEach() {
        mongoTemplate.dropCollection(CombinedSicActivitiesStorageModel.class);
    }

    @Test
    @DisplayName("Return results for a search by sic code and check score is not null")
    void sicCodeSearch () {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("29201").getTextCriteriaMatchPhrase());
        
        assertEquals(1, results.size());
        assertNotNull(results.get(0).getScore());

        assertThat(results, containsInAnyOrder(SicCodeTestData.BUS_MANUFACTURE_STORAGE_MODEL));
    }

    @Test
    @DisplayName("Correct items are returns in the correct order (since each result item has differing number of matches)")
    void textSearchThreeItemsAnyMatchVerifySorting() {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("Bean Growing Organic").getTextCriteriaMatchAny());

        assertEquals(4, results.size());

        // ordered assertion with contains and number of matches are 3, 2, 1
        assertThat(results, contains( SicCodeTestData.BEAN_GROWING_ORGANIC_STORAGE_MODEL,
                                      SicCodeTestData.BEAN_GROWING_STORAGE_MODEL,
                                      SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL,
                                      SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL));
    }

    @Test
    @DisplayName("Correct two items for a search with one search word")
    void textSearchOneItemAnyMatch() {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("manufacture").getTextCriteriaMatchAny());
        
        assertEquals(3, results.size());

        assertThat(results, containsInAnyOrder( SicCodeTestData.BARLEY_MALTING_STORAGE_MODEL,
                                                SicCodeTestData.BUS_MANUFACTURE_STORAGE_MODEL,
                                                SicCodeTestData.BEAN_GROWING_ORGANIC_STORAGE_MODEL));
    }

    @Test
    @DisplayName("Correct five items for a search with two search word")
    void textSearchTwoItemAnyMatch() {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("Barley growing").getTextCriteriaMatchAny());
        
        assertEquals(5, results.size());
        
        assertThat(results, containsInAnyOrder( SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL, 
                                                SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL, 
                                                SicCodeTestData.BARLEY_MALTING_STORAGE_MODEL, 
                                                SicCodeTestData.BEAN_GROWING_STORAGE_MODEL, 
                                                SicCodeTestData.BEAN_GROWING_ORGANIC_STORAGE_MODEL));
    }

    @Test
    @DisplayName("Correct item for a `phase match` search with a phase of two words")
    void textSearchTwoItemPhraseMatch () {

        var results = combinedSicActivitiesRepository.findAllByOrderByScore(
            new SicCodeSearchTextCriteria("barley farming").getTextCriteriaMatchPhrase());
        
        assertEquals(1, results.size());

        assertThat(results, containsInAnyOrder(SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL));
    }

}
