package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.siccode.api.SicCodeApiApplication;

@RestController
public class SicCodeController {

    private static final Logger LOG = LoggerFactory.getLogger(SicCodeApiApplication.APPLICATION_NAME_SPACE);

    private final SicCodeService sicCodeService;

    private final CombinedSicActivitiesMapper mapper;

    private final CondensedSicCodesMapper condensedSicCodesMapper;

    @Autowired
    public SicCodeController(SicCodeService sicCodeService, CombinedSicActivitiesMapper mapper,
                             CondensedSicCodesMapper condensedSicCodesMapper) {
        this.sicCodeService = sicCodeService;
        this.mapper = mapper;
        this.condensedSicCodesMapper = condensedSicCodesMapper;
    }
    
    @PostMapping(value = "/internal/sic-code-search/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CombinedSicActivitiesApiModel> post(
        @RequestBody SicCodeSearchRequestApiModel searchModel, 
        @RequestHeader(value = "X-Request-Id",required = true) String xRequestId){

            LOG.infoContext(xRequestId, "using search_string: " + searchModel.getSearchString(), null);

           return mapper.storageModelToApiModel(sicCodeService.search(xRequestId, searchModel));
    }

    @GetMapping(value = "/internal/condensed-sic-codes", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CondensedSicCodesApiModel> getCondensedSicCodes() {
        LOG.info("Requesting full list of condensed SIC Code data");

        return condensedSicCodesMapper.storageModelListToApiModelList(sicCodeService.retrieveCondensedSicCodes());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void uncaughtException(Exception e) {
        LOG.error("Something unexpected has occurred: " + e.getMessage(), e);
    }

}
