package uk.gov.companieshouse.siccode.api.search;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CondensedSicCodesMapper {

    List<CondensedSicCodesApiModel> storageModelListToApiModelList(List<CondensedSicCodesStorageModel> condensedSicCodesStorageModelList);
}
