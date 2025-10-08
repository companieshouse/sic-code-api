package uk.gov.companieshouse.siccode.api.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.companieshouse.siccode.api.groups.TestType;

import java.util.ArrayList;
import java.util.List;

@Tag(TestType.UNIT)
@SpringBootTest
public class CondensedSicCodesMapperTest {

    private CondensedSicCodesMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(CondensedSicCodesMapper.class);
    }

    @Test
    public void testCondensedSicCodeStorageListToCondensedSicCodeApiList() {
        final int SEED_TEST_SIC_CODE = 12340;
        final String TEST_SIC_DESCRIPTION = "Description for ";

        final int NUMBER_OF_SIC_CODES = 5;

        List<CondensedSicCodesStorageModel> condensedSicCodesStorageModels = new ArrayList<>();
        List<CondensedSicCodesApiModel> expectedCondensedSicCodesApiModels = new ArrayList<>();

        for (int i = 1; i < NUMBER_OF_SIC_CODES + 1; i++) {
            String testSicCode = "" + (SEED_TEST_SIC_CODE + i);
            String testSicDescription = TEST_SIC_DESCRIPTION + testSicCode;

            condensedSicCodesStorageModels.add(new CondensedSicCodesStorageModel(testSicCode, testSicCode, testSicDescription));

            expectedCondensedSicCodesApiModels.add(new CondensedSicCodesApiModel(testSicCode, testSicDescription));
        }

        List<CondensedSicCodesApiModel> returnedCondensedSicCodesApiModels = mapper.storageModelListToApiModelList(condensedSicCodesStorageModels);

        assertNotNull(returnedCondensedSicCodesApiModels);
        assertEquals(NUMBER_OF_SIC_CODES, returnedCondensedSicCodesApiModels.size());
        assertEquals(expectedCondensedSicCodesApiModels, returnedCondensedSicCodesApiModels);
    }
}
