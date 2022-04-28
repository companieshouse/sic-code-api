package uk.gov.companieshouse.siccode.api.search;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.siccode.api.SicCodeApiApplication;

@RestController
public class HealthCheckController {

    public static final String HEALTH_CHECK_MESSAGE = "sic-code-api is alive";

    private static final Logger LOG = LoggerFactory.getLogger(SicCodeApiApplication.APPLICATION_NAME_SPACE);

    @GetMapping(value = "/internal/sic-code-search/healthcheck")
    public @ResponseBody ResponseEntity<String> heathCheck() {

        LOG.info("Service responded with: " + HttpStatus.OK);

        return new ResponseEntity<>(HEALTH_CHECK_MESSAGE, HttpStatus.OK);
    }
}
