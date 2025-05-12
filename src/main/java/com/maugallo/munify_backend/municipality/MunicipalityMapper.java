package com.maugallo.munify_backend.municipality;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MunicipalityMapper {

    MunicipalityResponseDTO toDTO(Municipality municipality);

}
