package uk.gov.companieshouse.siccode.api.search;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Mapper(componentModel = "spring")
public interface CombinedSicActivitiesStorageModelMapper {

    @Mapping(source = "CombinedSicActivitiesStorageModel.id", target = "id")
    @Mapping(source = "CombinedSicActivitiesStorageModel.sicCode", target = "sicCode")
    @Mapping(source = "CombinedSicActivitiesStorageModel.activityDescription", target = "activityDescription")
    @Mapping(source = "CombinedSicActivitiesStorageModel.activityDescriptionSearchField", target = "activityDescriptionSearchField")
    // Do we need activityDescriptionSearchField? This is only used for the search and is just a lowercase version of activityDescription
    @Mapping(source = "CombinedSicActivitiesStorageModel.sicDescription", target = "sicDescription")
    @Mapping(source = "CombinedSicActivitiesStorageModel.companiesHouseactivity", target = "companiesHouseactivity")

    CombinedSicActivitiesStorageModelDto dto(CombinedSicActivitiesStorageModel CombinedSicActivitiesStorageModel);
    
}
