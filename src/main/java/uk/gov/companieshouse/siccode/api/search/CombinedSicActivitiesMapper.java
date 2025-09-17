package uk.gov.companieshouse.siccode.api.search;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CombinedSicActivitiesMapper {

    public abstract List<CombinedSicActivitiesApiModel> storageModelToApiModel(List<CombinedSicActivitiesStorageModel> source);

    @Mapping(source = "source.sicCode", target = "sicCode")
    @Mapping(source = "source.activityDescription", target = "sicDescription")
    public abstract CondensedSicActivitiesApiModel storageModelToCondensedApiModel(CombinedSicActivitiesStorageModel source);

    public abstract List<CondensedSicActivitiesApiModel> storageModelListToCondensedApiModelList(List<CombinedSicActivitiesStorageModel> source);
}
