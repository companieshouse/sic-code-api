package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CombinedSicActivitiesMapper {

    public abstract List<CombinedSicActivitiesApiModel> storageModelToApiModel(List<CombinedSicActivitiesStorageModel> source);

}
