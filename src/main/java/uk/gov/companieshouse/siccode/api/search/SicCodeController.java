package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.siccode.api.SicCodeApiApplication;

@RestController
public class SicCodeController {

    private static final Logger LOG = LoggerFactory.getLogger(SicCodeApiApplication.APPLICATION_NAME_SPACE);

    private final SicCodeService sicCodeService;

    private final CombinedSicActivitiesMapper mapper;
    
    @Autowired
    public SicCodeController(SicCodeService sicCodeService, CombinedSicActivitiesMapper mapper) {
        this.sicCodeService = sicCodeService;
        this.mapper = mapper;
    }
    
    @PostMapping(value = "/internal/sic-code-search/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CombinedSicActivitiesApiModel> post(
        @RequestBody SicCodeSearchRequestApiModel searchModel, 
        @RequestHeader(value = "X-Request-Id",required = true) String xRequestId){

            LOG.infoContext(xRequestId, "using search_string: " + searchModel.getSearchString(), null);

           return mapper.storageModelToApiModel(sicCodeService.search(xRequestId, searchModel));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void uncaughtException(Exception e) {

        LOG.error("Something unexpected has occurred: " + e.getMessage(), e);
    }

}
