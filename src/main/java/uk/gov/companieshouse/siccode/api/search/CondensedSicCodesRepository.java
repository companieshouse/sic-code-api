package uk.gov.companieshouse.siccode.api.search;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondensedSicCodesRepository extends MongoRepository<CondensedSicCodesStorageModel, String>{

    List<CondensedSicCodesStorageModel> findAll();

}
