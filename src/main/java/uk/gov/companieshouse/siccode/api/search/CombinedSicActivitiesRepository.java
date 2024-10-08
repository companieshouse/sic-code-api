package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombinedSicActivitiesRepository extends MongoRepository<CombinedSicActivitiesStorageModel, String>{

    List<CombinedSicActivitiesStorageModel> findAllByOrderByScore(TextCriteria criteria);

}
