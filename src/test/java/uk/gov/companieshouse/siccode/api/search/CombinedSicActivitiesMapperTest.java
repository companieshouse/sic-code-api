package uk.gov.companieshouse.siccode.api.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import uk.gov.companieshouse.siccode.api.groups.TestType;

@Tag(TestType.UNIT)
public class CombinedSicActivitiesMapperTest {
 
    private CombinedSicActivitiesMapper mapper
    = Mappers.getMapper(CombinedSicActivitiesMapper.class);

    private final static CombinedSicActivitiesStorageModel BARLEY_FARMING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("10", "01110", "Barley Farming",
    "barley farming", "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesStorageModel BARLEY_GROWING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("15", "01110", "Barley growing",
    "barley growing", "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesStorageModel BEAN_GROWING_ORGANIC_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("17", "01110", "Bean growing organic",
    "bean growing organic", "Growing of cereals except rice, leguminous crops and oil seeds and manufacture", false);

    private final static CombinedSicActivitiesStorageModel BEAN_GROWING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("18", "01110", "Bean growing",
    "bean growing", "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesApiModel BARLEY_FARMING_API_MODEL = new CombinedSicActivitiesApiModel("10", "01110", "Barley Farming", 
    "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesApiModel BARLEY_GROWING_API_MODEL = new CombinedSicActivitiesApiModel("15", "01110", "Barley growing",
    "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesApiModel BEAN_GROWING_ORGANIC_API_MODEL = new CombinedSicActivitiesApiModel("17", "01110",
    "Bean growing organic","Growing of cereals except rice, leguminous crops and oil seeds and manufacture", false);

    private final static CombinedSicActivitiesApiModel BEAN_GROWING_API_MODEL = new CombinedSicActivitiesApiModel("18", "01110", "Bean growing",
    "Growing of cereals except rice, leguminous crops and oil seeds", false);

    @Test
    @DisplayName("Check That List Of StorageModels Map To List Of ApiModels")
    public void checkListOfStorageModelsMapToListOfApiModels() {
        
        List<CombinedSicActivitiesStorageModel> storageModelList = new ArrayList<>();

        storageModelList.add(BARLEY_FARMING_STORAGE_MODEL);
        storageModelList.add(BARLEY_GROWING_STORAGE_MODEL);
        storageModelList.add(BEAN_GROWING_ORGANIC_STORAGE_MODEL);
        storageModelList.add(BEAN_GROWING_STORAGE_MODEL);

        // When
       var apiModelList = mapper.storageModelToApiModel(storageModelList);

        // Then
        assertEquals(4, apiModelList.size());
        assertTrue(apiModelList.contains(BARLEY_FARMING_API_MODEL));
        assertTrue(apiModelList.contains(BARLEY_GROWING_API_MODEL));
        assertTrue(apiModelList.contains(BEAN_GROWING_ORGANIC_API_MODEL));
        assertTrue(apiModelList.contains(BEAN_GROWING_API_MODEL));

    }

}
