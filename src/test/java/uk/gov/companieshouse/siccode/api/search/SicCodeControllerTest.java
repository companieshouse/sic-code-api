package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import uk.gov.companieshouse.api.util.security.EricConstants;

import uk.gov.companieshouse.siccode.api.groups.TestType;

@Tag(TestType.UNIT)
@WebMvcTest(controllers = SicCodeController.class)
public class SicCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SicCodeService sicCodeService;

    @MockBean
    private CombinedSicActivitiesMapper mapper;

    @Test
    @DisplayName("Successful search with calls to service and mapper classes")
    void successfulSearch() throws Exception {

        var storageModelList = new ArrayList<CombinedSicActivitiesStorageModel>();
        storageModelList.add(SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL);
        storageModelList.add(SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL);

        var apiModelList = new ArrayList<CombinedSicActivitiesApiModel>();
        apiModelList.add(SicCodeTestData.BARLEY_FARMING_API_MODEL);
        apiModelList.add(SicCodeTestData.BARLEY_GROWING_API_MODEL);

        when(sicCodeService.search(any(SicCodeSearchRequestApiModel.class))).thenReturn(storageModelList);

        when(mapper.storageModelToApiModel(storageModelList)).thenReturn(apiModelList);

        mockMvc.perform(addAuthentication(post("/internal/sic-code-search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"context_id\":\"111\",\"search_string\": \"Barley Farming\", \"match_phrase\": false}")
                .accept(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItems("10", "15")))
                .andExpect(jsonPath("$[*].sic_code", hasItems("01110")))
                .andExpect(jsonPath("$[*].activity_description", hasItems("Barley Farming")))
                .andExpect(jsonPath("$[*].activity_description", hasItems("Barley growing")));
    }

    @Test
    @DisplayName("Successful search with calls to service and mapper classes")
    void shouldCatchUncaughtExceptionInController() throws Exception {

        when(sicCodeService.search(any(SicCodeSearchRequestApiModel.class)))
                .thenThrow(new RuntimeException("Test exception"));

        mockMvc.perform(addAuthentication(post("/internal/sic-code-search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"context_id\":\"111\",\"search_string\": \"Barley Farming\", \"match_phrase\": false}"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Return with 401 Unauthorized if unauthenticated")
    void getReturns401IfUnauthenticated() throws Exception {


        mockMvc.perform(post("/internal/sic-code-search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"context_id\":\"111\",\"search_string\": \"Barley Farming\", \"match_phrase\": false}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private MockHttpServletRequestBuilder addAuthentication(MockHttpServletRequestBuilder request) {
        return request
            .header(EricConstants.ERIC_IDENTITY, "test-id")
            .header(EricConstants.ERIC_IDENTITY_TYPE, EricConstants.ERIC_IDENTITY_TYPE)
            .header(EricConstants.ERIC_AUTHORISED_KEY_ROLES, EricConstants.ERIC_AUTHORISED_KEY_ROLES);
    }
}