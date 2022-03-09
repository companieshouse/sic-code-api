package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

/**
 * SicCodeController
 */

@RestController
public class SicCodeController {

    private final SicCodeService sicCodeService;

    private final CombinedSicActivitiesMapper mapper;
    
    @Autowired
    public SicCodeController(SicCodeService sicCodeService, CombinedSicActivitiesMapper mapper) {
        this.sicCodeService = sicCodeService;
        this.mapper = mapper;
    }
    
    @PostMapping(value = "/sic-code-search")
    @ResponseStatus(HttpStatus.OK)
    public List<CombinedSicActivitiesApiModel> post(
        @RequestBody SicCodeSearchRequestApiModel searchModel){

           return mapper.storageModelToApiModel(sicCodeService.search(searchModel));
    }
}
