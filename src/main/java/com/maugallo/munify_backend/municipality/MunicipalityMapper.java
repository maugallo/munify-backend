package com.maugallo.munify_backend.municipality;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.municipality.dto.MunicipalityRequestDTO;
import com.maugallo.munify_backend.municipality.dto.MunicipalityResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface MunicipalityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "logoUrl", source = "logoUrl")
    @Mapping(target = "primaryColor", source = "primaryColor")
    @Mapping(target = "secondaryColor", source = "secondaryColor")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isEnabled", constant = "true")
    @Mapping(target = "features", ignore = true)
    Municipality toEntity(MunicipalityRequestDTO municipality);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "logoUrl", source = "logoUrl")
    @Mapping(target = "primaryColor", source = "primaryColor")
    @Mapping(target = "secondaryColor", source = "secondaryColor")
    @Mapping(target = "features", source = "features")
    MunicipalityResponseDTO toResponse(Municipality municipality);

}
