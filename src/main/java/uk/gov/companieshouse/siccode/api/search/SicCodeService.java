package uk.gov.companieshouse.siccode.api.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

@Service
public class SicCodeService {

    @Autowired
    private CombinedSicActivitiesRepository combinedSicActivitiesRepository;

    public  List<CombinedSicActivitiesStorageModel> search(SicCodeSearchRequestApiModel sicCodeSearchRequestApiModel) {

        if (StringUtils.isEmpty(sicCodeSearchRequestApiModel.getSearchString())) {
            return new ArrayList<>();
        }

        TextCriteria criteria = null;
        if (sicCodeSearchRequestApiModel.isMatchPhrase()) {
            criteria = new SicCodeSearchTextCriteria(sicCodeSearchRequestApiModel.getSearchString()).getTextCriteriaMatchPhrase();
        }
        else {
            criteria = new SicCodeSearchTextCriteria(sicCodeSearchRequestApiModel.getSearchString()).getTextCriteriaMatchAny();
        }

        return combinedSicActivitiesRepository.findAllByOrderByScore(criteria);
    }
    
}
