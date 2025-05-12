package com.maugallo.munify_backend.citizen;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitizenMapper {

    Citizen toEntity(CitizenRequestDTO citizen);

    CitizenResponseDTO toDTO(Citizen citizen);

}
