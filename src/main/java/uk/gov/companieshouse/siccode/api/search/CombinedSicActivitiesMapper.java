package uk.gov.companieshouse.siccode.api.search;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CombinedSicActivitiesMapper {

    List<CombinedSicActivitiesApiModel> storageModelToApiModel(List<CombinedSicActivitiesStorageModel> source);
}
