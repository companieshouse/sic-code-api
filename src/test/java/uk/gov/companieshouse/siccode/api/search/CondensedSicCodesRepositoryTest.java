package uk.gov.companieshouse.siccode.api.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.ARMOURED_CAR_SERVICES_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_FARMING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_GROWING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_MALTING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BEAN_GROWING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BEAN_GROWING_ORGANIC_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BUS_MANUFACTURE_CONDENSED_STORAGE_MODEL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import uk.gov.companieshouse.siccode.api.groups.TestType;

import java.util.List;

@Tag(TestType.INTEGRATION)
@DataMongoTest
public class CondensedSicCodesRepositoryTest {

    @Autowired
    private CondensedSicCodesRepository condensedSicCodesRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void beforeEach() {
        mongoTemplate.createCollection(CondensedSicCodesStorageModel.class);
        mongoTemplate.insert(BARLEY_FARMING_CONDENSED_STORAGE_MODEL);
        mongoTemplate.insert(BARLEY_GROWING_CONDENSED_STORAGE_MODEL);
        mongoTemplate.insert(BEAN_GROWING_ORGANIC_CONDENSED_STORAGE_MODEL);

        mongoTemplate.insert(ARMOURED_CAR_SERVICES_CONDENSED_STORAGE_MODEL);
        mongoTemplate.insert(BARLEY_MALTING_CONDENSED_STORAGE_MODEL);
        mongoTemplate.insert(BUS_MANUFACTURE_CONDENSED_STORAGE_MODEL);
    }

    @AfterEach
    public void tearDown() {
        mongoTemplate.dropCollection(CondensedSicCodesStorageModel.class);
    }

    @Test
    public void shouldRetrieveAllCombinedSicCodes() {
        List<CondensedSicCodesStorageModel> condensedSicCodesStorageModelList = condensedSicCodesRepository.findAll();

        assertEquals(6, condensedSicCodesStorageModelList.size());
        assertTrue(condensedSicCodesStorageModelList.contains(BARLEY_FARMING_CONDENSED_STORAGE_MODEL));
        assertTrue(condensedSicCodesStorageModelList.contains(BARLEY_GROWING_CONDENSED_STORAGE_MODEL));
        assertTrue(condensedSicCodesStorageModelList.contains(BEAN_GROWING_ORGANIC_CONDENSED_STORAGE_MODEL));
        assertFalse(condensedSicCodesStorageModelList.contains(BEAN_GROWING_CONDENSED_STORAGE_MODEL));
        assertTrue(condensedSicCodesStorageModelList.contains(ARMOURED_CAR_SERVICES_CONDENSED_STORAGE_MODEL));
        assertTrue(condensedSicCodesStorageModelList.contains(BARLEY_MALTING_CONDENSED_STORAGE_MODEL));
        assertTrue(condensedSicCodesStorageModelList.contains(BUS_MANUFACTURE_CONDENSED_STORAGE_MODEL));
    }
}
