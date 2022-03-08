package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SicCodeController {

    private final SicCodeService sicCodeService;
    
    @Autowired
    public SicCodeController(SicCodeService sicCodeService) {
        this.sicCodeService = sicCodeService;
    }
    
    @PostMapping(value = "/sic-code-search")
    public List<CombinedSicActivitiesStorageModel> post(
        @RequestBody SicCodeSearchRequestApiModel searchModel){

        return sicCodeService.search(searchModel);
    }
}
