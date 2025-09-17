package uk.gov.companieshouse.siccode.api.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import uk.gov.companieshouse.siccode.api.groups.TestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(TestType.UNIT)
public class CombinedSicActivitiesMapperTest {
 
    private CombinedSicActivitiesMapper mapper = Mappers.getMapper(CombinedSicActivitiesMapper.class);

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

    @Test
    @DisplayName("Check That List Of StorageModels Map To List Of Condensed ApiModels")
    public void checkListOfStorageModelsMapToListOfCondensedApiModels() {

        var storageModelList = Arrays.asList(SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL,
                SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL,
                SicCodeTestData.BEAN_GROWING_STORAGE_MODEL,
                SicCodeTestData.BEAN_GROWING_ORGANIC_STORAGE_MODEL,
                SicCodeTestData.ARMOURED_CAR_SERVICES_STORAGE_MODEL,
                SicCodeTestData.BARLEY_MALTING_STORAGE_MODEL,
                SicCodeTestData.BUS_MANUFACTURE_STORAGE_MODEL);

        List<CondensedSicActivitiesApiModel> mappedCondensedApiModelList = mapper.storageModelListToCondensedApiModelList(storageModelList);

        assertEquals(7, mappedCondensedApiModelList.size());
        assertThat(mappedCondensedApiModelList,containsInAnyOrder(SicCodeTestData.BARLEY_FARMING_CONDENSED_API_MODEL,
                SicCodeTestData.BARLEY_GROWING_CONDENSED_API_MODEL,
                SicCodeTestData.BEAN_GROWING_CONDENSED_API_MODEL,
                SicCodeTestData.BEAN_GROWING_ORGANIC_CONDENSED_API_MODEL,
                SicCodeTestData.BUS_MANUFACTURE_CONDENSED_API_MODEL,
                SicCodeTestData.ARMOURED_CAR_SERVICES_CONDENSED_API_MODEL,
                SicCodeTestData.BARLEY_MALTING_CONDENSED_API_MODEL
                ));
    }
}
