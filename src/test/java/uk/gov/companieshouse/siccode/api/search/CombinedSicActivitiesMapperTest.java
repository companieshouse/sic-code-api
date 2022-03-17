package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import uk.gov.companieshouse.siccode.api.groups.TestType;

@Tag(TestType.UNIT)
public class CombinedSicActivitiesMapperTest {
 
    private CombinedSicActivitiesMapper mapper
    = Mappers.getMapper(CombinedSicActivitiesMapper.class);

    @Test
    @DisplayName("Check That List Of StorageModels Map To List Of ApiModels")
    public void checkListOfStorageModelsMapToListOfApiModels() {

        var storageModelList = new ArrayList<CombinedSicActivitiesStorageModel>();
        storageModelList.add(SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL);
        storageModelList.add(SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL);
        storageModelList.add(SicCodeTestData.BEAN_GROWING_STORAGE_MODEL);
        storageModelList.add(SicCodeTestData.BEAN_GROWING_ORGANIC_STORAGE_MODEL);

        // When
       var mappedApiModelList = mapper.storageModelToApiModel(storageModelList);

        // Then
        assertEquals(4, mappedApiModelList.size());
        assertThat(mappedApiModelList,containsInAnyOrder(SicCodeTestData.BARLEY_FARMING_API_MODEL,
                                                         SicCodeTestData.BARLEY_GROWING_API_MODEL,
                                                         SicCodeTestData.BEAN_GROWING_API_MODEL,
                                                         SicCodeTestData.BEAN_GROWING_ORGANIC_API_MODEL));
    }
}
