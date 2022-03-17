package uk.gov.companieshouse.siccode.api.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
    
    @Autowired
    public SicCodeService(CombinedSicActivitiesRepository combinedSicActivitiesRepository) {
        this.combinedSicActivitiesRepository = combinedSicActivitiesRepository;
    }

    public  List<CombinedSicActivitiesStorageModel> search(SicCodeSearchRequestApiModel sicCodeSearchRequestApiModel) {

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

        LOG.infoContext(sicCodeSearchRequestApiModel.getContextId(), "Search Complete", dataMap(sicCodeSearchRequestApiModel,combinedSicActivityOrderedResults));

        return combinedSicActivityOrderedResults;
    }

    private Map<String, Object> dataMap(SicCodeSearchRequestApiModel sicCodeSearchRequestApiModel,
            List<CombinedSicActivitiesStorageModel> combinedSicActivityOrderedResults) {
                Map<String, Object> result = new HashMap<>();
                result.put("search_string", sicCodeSearchRequestApiModel.getSearchString());
                result.put("match_phrase", sicCodeSearchRequestApiModel.isMatchPhrase());
                result.put("number_of_matches", combinedSicActivityOrderedResults.size());
        
                return result;
    }
    
}
