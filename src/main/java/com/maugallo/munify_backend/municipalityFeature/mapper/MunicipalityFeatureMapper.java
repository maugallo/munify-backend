package com.maugallo.munify_backend.municipalityFeature.mapper;

import com.maugallo.munify_backend.municipalityFeature.MunicipalityFeature;
import com.maugallo.munify_backend.municipalityFeature.dto.MunicipalityFeatureResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MunicipalityFeatureMapper {

    @Mapping(source = "feature.id", target = "featureId")
    @Mapping(source = "feature.name", target = "name")
    @Mapping(source = "feature.isEnabled", target = "isEnabled")
    MunicipalityFeatureResponseDTO toDTO(MunicipalityFeature municipalityFeature);

}
