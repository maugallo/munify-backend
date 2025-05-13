package com.maugallo.munify_backend.municipality.mapper;

import com.maugallo.munify_backend.municipality.Municipality;
import com.maugallo.munify_backend.municipality.dto.MunicipalityResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MunicipalityMapper {

    MunicipalityResponseDTO toDTO(Municipality municipality);

}
