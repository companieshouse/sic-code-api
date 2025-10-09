package uk.gov.companieshouse.siccode.api.search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.siccode.api.SicCodeApiApplication;

@Service
public class SicCodeService {

    private static final Logger LOG = LoggerFactory.getLogger(SicCodeApiApplication.APPLICATION_NAME_SPACE);
    
    private final CombinedSicActivitiesRepository combinedSicActivitiesRepository;
    private final CondensedSicCodesRepository condensedSicCodesRepository;

    @Autowired
    public SicCodeService(CombinedSicActivitiesRepository combinedSicActivitiesRepository,
                          CondensedSicCodesRepository condensedSicCodesRepository) {
        this.combinedSicActivitiesRepository = combinedSicActivitiesRepository;
        this.condensedSicCodesRepository = condensedSicCodesRepository;
    }

    public  List<CombinedSicActivitiesStorageModel> search(String xRequestId, SicCodeSearchRequestApiModel sicCodeSearchRequestApiModel) {

        var searchString = sicCodeSearchRequestApiModel.getSearchString();
        if (StringUtils.isEmpty(searchString)) {
            return new ArrayList<>();
        }

        TextCriteria criteria = null;
        if (sicCodeSearchRequestApiModel.isMatchPhrase()) {
            criteria = new SicCodeSearchTextCriteria(searchString).getTextCriteriaMatchPhrase();
        }
        else {
            criteria = new SicCodeSearchTextCriteria(searchString).getTextCriteriaMatchAny();
        }

        var combinedSicActivityOrderedResults = combinedSicActivitiesRepository.findAllByOrderByScore(criteria);

        LOG.infoContext(xRequestId, "Search Complete", dataMap(sicCodeSearchRequestApiModel,combinedSicActivityOrderedResults));

        return combinedSicActivityOrderedResults;
    }

    public List<CombinedSicActivitiesStorageModel> getAll() {
        return combinedSicActivitiesRepository.findAll();
    }

    public List<CondensedSicCodesStorageModel> retrieveCondensedSicCodes() {
        return condensedSicCodesRepository.findAll();
    }

    private Map<String, Object> dataMap(SicCodeSearchRequestApiModel sicCodeSearchRequestApiModel,
            List<CombinedSicActivitiesStorageModel> combinedSicActivityOrderedResults) {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("search_string", sicCodeSearchRequestApiModel.getSearchString());
                result.put("match_phrase", sicCodeSearchRequestApiModel.isMatchPhrase());
                result.put("number_of_matches", combinedSicActivityOrderedResults.size());
        
                return result;
    }
    
}
