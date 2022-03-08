package uk.gov.companieshouse.siccode.api.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

@Service
public class SicCodeService {

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

        return combinedSicActivitiesRepository.findAllByOrderByScore(criteria);
    }
    
}
