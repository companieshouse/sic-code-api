package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CombinedSicActivitiesRepository extends MongoRepository<CombinedSicActivitiesStorageModel, String>{

    @Query("{ 'activity_description_lower_case' : { $regex: ?0 } }")
    List<CombinedSicActivitiesStorageModel> findByActivityDescriptionLowerCaseRegex(String regex);    
    
}
