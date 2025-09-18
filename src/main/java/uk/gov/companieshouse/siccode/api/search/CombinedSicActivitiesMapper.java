package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CombinedSicActivitiesMapper {

    public abstract List<CombinedSicActivitiesApiModel> storageModelToApiModel(List<CombinedSicActivitiesStorageModel> source);

    @Mapping(source = "sicCode", target = "sicCode")
    @Mapping(source = "activityDescription", target = "sicDescription")
    public abstract CondensedSicActivitiesApiModel storageModelToCondensedApiModel(CombinedSicActivitiesStorageModel source);

    public abstract List<CondensedSicActivitiesApiModel> storageModelListToCondensedApiModelList(List<CombinedSicActivitiesStorageModel> source);
}
