package uk.gov.companieshouse.siccode.api.search;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import uk.gov.companieshouse.api.util.security.EricConstants;
import uk.gov.companieshouse.siccode.api.groups.TestType;

@Tag(TestType.UNIT)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HealthCheckController.class)
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void validateIsHealthy() throws Exception {

        mockMvc.perform(get("/internal/sic-code-search/healthcheck"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(HealthCheckController.HEALTH_CHECK_MESSAGE)));
    }

    private MockHttpServletRequestBuilder addAuthentication(MockHttpServletRequestBuilder request) {
        return request
            .header(EricConstants.ERIC_IDENTITY, "test-id")
            .header(EricConstants.ERIC_IDENTITY_TYPE, "key")
            .header(EricConstants.ERIC_AUTHORISED_KEY_ROLES, "*");
    }
}
