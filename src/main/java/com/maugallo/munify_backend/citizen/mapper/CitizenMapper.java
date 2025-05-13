package com.maugallo.munify_backend.citizen.mapper;

import com.maugallo.munify_backend.citizen.Citizen;
import com.maugallo.munify_backend.citizen.dto.CitizenRequestDTO;
import com.maugallo.munify_backend.citizen.dto.CitizenResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitizenMapper {

    Citizen toEntity(CitizenRequestDTO citizen);

    CitizenResponseDTO toDTO(Citizen citizen);

}
