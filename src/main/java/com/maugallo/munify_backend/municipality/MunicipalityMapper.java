package com.maugallo.munify_backend.municipality;

import com.maugallo.munify_backend.municipality.dto.MunicipalityResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MunicipalityMapper {

    MunicipalityResponseDTO toDTO(Municipality municipality);

}
