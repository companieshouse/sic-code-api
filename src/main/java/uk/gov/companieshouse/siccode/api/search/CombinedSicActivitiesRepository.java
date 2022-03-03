package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CombinedSicActivitiesRepository extends MongoRepository<CombinedSicActivitiesStorageModel, String>{

    @Query("{ 'activity_description_lower_case' : { $regex: ?0 } }")
    List<CombinedSicActivitiesStorageModel> findByActivityDescriptionSearchFieldRegex(String regex);    
    
    // Execute a full-text search and define sorting dynamically
    List<CombinedSicActivitiesStorageModel> findAllBy(TextCriteria criteria, Sort sort);

    List<CombinedSicActivitiesStorageModel> findAllBy(TextCriteria criteria);

    List<CombinedSicActivitiesStorageModel> findAllByOrderByScore(TextCriteria criteria);


}
