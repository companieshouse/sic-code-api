package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.ARMOURED_CAR_SERVICES_CONDENSED_API_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.ARMOURED_CAR_SERVICES_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_FARMING_CONDENSED_API_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_FARMING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_GROWING_CONDENSED_API_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_GROWING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BARLEY_MALTING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BEAN_GROWING_CONDENSED_API_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BEAN_GROWING_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BEAN_GROWING_ORGANIC_CONDENSED_API_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BEAN_GROWING_ORGANIC_CONDENSED_STORAGE_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BUS_MANUFACTURE_CONDENSED_API_MODEL;
import static uk.gov.companieshouse.siccode.api.search.SicCodeTestData.BUS_MANUFACTURE_CONDENSED_STORAGE_MODEL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import uk.gov.companieshouse.api.util.security.EricConstants;
import uk.gov.companieshouse.siccode.api.groups.TestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag(TestType.UNIT)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = SicCodeController.class)
class SicCodeControllerTest {

    private static final String X_REQUEST_ID = "your-request-id";
    private static final String ERIC_REQUEST_ID_KEY = "X-Request-Id";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SicCodeService sicCodeService;

    @MockitoBean
    private CombinedSicActivitiesMapper mapper;

    @MockitoBean
    private CondensedSicCodesMapper condensedSicCodesMapper;

    @Test
    @DisplayName("Successful search with calls to service and mapper classes")
    void successfulSearch() throws Exception {

        var storageModelList = new ArrayList<CombinedSicActivitiesStorageModel>();
        storageModelList.add(SicCodeTestData.BARLEY_FARMING_STORAGE_MODEL);
        storageModelList.add(SicCodeTestData.BARLEY_GROWING_STORAGE_MODEL);

        var apiModelList = new ArrayList<CombinedSicActivitiesApiModel>();
        apiModelList.add(SicCodeTestData.BARLEY_FARMING_API_MODEL);
        apiModelList.add(SicCodeTestData.BARLEY_GROWING_API_MODEL);

        when(sicCodeService.search(eq(X_REQUEST_ID), any(SicCodeSearchRequestApiModel.class))).thenReturn(storageModelList);

        when(mapper.storageModelToApiModel(storageModelList)).thenReturn(apiModelList);

        mockMvc.perform(addAuthentication(post("/internal/sic-code-search/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"search_string\": \"Barley Farming\", \"match_phrase\": false}")
                .accept(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].sic_code", hasItems("01110")))
                .andExpect(jsonPath("$[*].activity_description", hasItems("Barley Farming")))
                .andExpect(jsonPath("$[*].activity_description", hasItems("Barley growing")));
    }

    @Test
    @DisplayName("catching runtime exception")
    void shouldCatchUncaughtExceptionInController() throws Exception {

        when(sicCodeService.search(eq(X_REQUEST_ID), any(SicCodeSearchRequestApiModel.class)))
                .thenThrow(new RuntimeException("Test exception"));

        mockMvc.perform(addAuthentication(post("/internal/sic-code-search/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"search_string\": \"Barley Farming\", \"match_phrase\": false}"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Return with 401 Unauthorized if unauthenticated")
    void getReturns401IfUnauthenticated() throws Exception {

        mockMvc.perform(post("/internal/sic-code-search/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"111\",\"search_string\": \"Barley Farming\", \"match_phrase\": false}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return all condensed sic codes")
    void shouldReturnAllCondensedSicCodes() throws Exception {

        List<CondensedSicCodesStorageModel> storageModelList = Arrays.asList(ARMOURED_CAR_SERVICES_CONDENSED_STORAGE_MODEL, BARLEY_FARMING_CONDENSED_STORAGE_MODEL,
                BARLEY_GROWING_CONDENSED_STORAGE_MODEL, BARLEY_MALTING_CONDENSED_STORAGE_MODEL, BEAN_GROWING_CONDENSED_STORAGE_MODEL, BEAN_GROWING_ORGANIC_CONDENSED_STORAGE_MODEL,
                BUS_MANUFACTURE_CONDENSED_STORAGE_MODEL);

        List<CondensedSicCodesApiModel> apiCondensedModelList = Arrays.asList(BARLEY_FARMING_CONDENSED_API_MODEL,
                BARLEY_FARMING_CONDENSED_API_MODEL, BARLEY_GROWING_CONDENSED_API_MODEL, BEAN_GROWING_CONDENSED_API_MODEL,
                BEAN_GROWING_ORGANIC_CONDENSED_API_MODEL, BUS_MANUFACTURE_CONDENSED_API_MODEL,
                ARMOURED_CAR_SERVICES_CONDENSED_API_MODEL);


        when(sicCodeService.retrieveCondensedSicCodes()).thenReturn(storageModelList);

        when(condensedSicCodesMapper.storageModelListToApiModelList(storageModelList)).thenReturn(apiCondensedModelList);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(addAuthentication(get("/internal/condensed-sic-codes"))).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(apiCondensedModelList)));
    }

    private MockHttpServletRequestBuilder addAuthentication(MockHttpServletRequestBuilder request) {
        return request
            .header(ERIC_REQUEST_ID_KEY, X_REQUEST_ID)
            .header(EricConstants.ERIC_IDENTITY, "test-id")
            .header(EricConstants.ERIC_IDENTITY_TYPE, "key")
            .header(EricConstants.ERIC_AUTHORISED_KEY_ROLES, "*");
    }
}