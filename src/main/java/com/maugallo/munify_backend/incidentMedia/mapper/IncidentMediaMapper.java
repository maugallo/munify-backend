package com.maugallo.munify_backend.incidentMedia.mapper;

import com.maugallo.munify_backend.incidentMedia.IncidentMedia;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaRequestDTO;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { IncidentMapperHelper.class })
public interface IncidentMediaMapper {

    @Mapping(target = "incident", source = "incidentId")
    IncidentMedia toEntity(IncidentMediaRequestDTO incidentMedia);

    IncidentMediaResponseDTO toDTO(IncidentMedia incidentMedia);

}
