package com.maugallo.munify_backend.municipalityFeature;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MunicipalityFeatureMapper {

    @Mapping(source = "feature.id", target = "featureId")
    @Mapping(source = "feature.name", target = "name")
    @Mapping(source = "feature.isEnabled", target = "isEnabled")
    MunicipalityFeatureResponseDTO toDTO(MunicipalityFeature municipalityFeature);

}
