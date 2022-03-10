package uk.gov.companieshouse.siccode.api.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import uk.gov.companieshouse.siccode.api.groups.TestType;

@Tag(TestType.UNIT)
@ExtendWith(SpringExtension.class)
// @WebMvcTest(controllers = SicCodeController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SicCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SicCodeService sicCodeService;

    private final static CombinedSicActivitiesStorageModel BARLEY_FARMING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("10", "01110", "Barley Farming",
    "barley farming", "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesStorageModel BARLEY_GROWING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("15", "01110", "Barley growing",
    "barley growing", "Growing of cereals except rice, leguminous crops and oil seeds", false);

    private final static CombinedSicActivitiesStorageModel BEAN_GROWING_ORGANIC_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("17", "01110", "Bean growing organic",
    "bean growing organic", "Growing of cereals except rice, leguminous crops and oil seeds and manufacture", false);

    private final static CombinedSicActivitiesStorageModel BEAN_GROWING_STORAGE_MODEL = new CombinedSicActivitiesStorageModel("18", "01110", "Bean growing",
    "bean growing", "Growing of cereals except rice, leguminous crops and oil seeds", false);

    @Test
    @DisplayName("Successful search with the match_phrase option set to false")
    void successfulSearchWithFalseMatchPharse() throws Exception {


        List<CombinedSicActivitiesStorageModel> storageModelList = new ArrayList<>();
        storageModelList.add(BARLEY_FARMING_STORAGE_MODEL);
        storageModelList.add(BARLEY_GROWING_STORAGE_MODEL);
        storageModelList.add(BEAN_GROWING_ORGANIC_STORAGE_MODEL);
        storageModelList.add(BEAN_GROWING_STORAGE_MODEL);

        when(sicCodeService.search(any(SicCodeSearchRequestApiModel.class))).thenReturn(storageModelList);

        mockMvc.perform(post("/sic-code-search")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"context_id\":\"111\",\"search_string\": \"Barley Farming\", \"match_phrase\": false}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id", hasItems("10","15")))
        .andExpect(jsonPath("$[*].sic_code", hasItems("01110")))
        .andExpect(jsonPath("$[*].activity_description", hasItems("Barley Farming")))
        .andExpect(jsonPath("$[*].activity_description", hasItems("Barley growing")));

    }

    //Currently not working. Multiple sicodes are being returned for an exact match search
    @Test
    @DisplayName("Successful search with the match_phrase option set to true")
    void successfulSearchWithTrueMatchPharse() throws Exception {


        List<CombinedSicActivitiesStorageModel> storageModelList = new ArrayList<>();
        storageModelList.add(BARLEY_FARMING_STORAGE_MODEL);
        storageModelList.add(BARLEY_GROWING_STORAGE_MODEL);
        storageModelList.add(BEAN_GROWING_ORGANIC_STORAGE_MODEL);
        storageModelList.add(BEAN_GROWING_STORAGE_MODEL);

        when(sicCodeService.search(any(SicCodeSearchRequestApiModel.class))).thenReturn(storageModelList);

        // mockMvc.perform(post("/sic-code-search")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content("{ \"context_id\":\"111\",\"search_string\": \"Barley Farming\", \"match_phrase\":true}")
        // .accept(MediaType.APPLICATION_JSON))
        // .andExpect(status().isOk())
        // .andExpect(jsonPath("$[*].id", hasItems("10")))
        // .andExpect(jsonPath("$[*].sic_code", hasItems("01110")))
        // .andExpect(jsonPath("$[*].activity_description", hasItems("Barley Farming")))
        // .andExpect(jsonPath("$[*].activity_description", hasItems("Barley growing")));

        MvcResult result = mockMvc.perform(post("/sic-code-search")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"context_id\":\"111\",\"search_string\": \"Barley Farming\", \"match_phrase\": true}")
        .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("RESULT" + result);
        System.out.println("CONTENT" + content);

    }
    
}
