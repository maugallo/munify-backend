package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import com.maugallo.munify_backend.media.MediaLinkBuilder;
import com.maugallo.munify_backend.incidentMedia.IncidentMediaMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class, uses = { IncidentMediaMapper.class })
public interface IncidentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", constant = "PENDIENTE")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isEnabled", constant = "true")
    @Mapping(target = "municipalityId", source = "municipalityId")
    @Mapping(target = "citizenId", source = "citizenId")
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "medias", ignore = true) // las medias se asocian en el servicio
    @Mapping(target = "municipality", ignore = true)
    @Mapping(target = "citizen", ignore = true)
    @Mapping(target = "employee", ignore = true)
    Incident toEntity(IncidentRequestDTO incident);

    /* Medias without readable urls. */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "citizenId", source = "citizenId")
    @Mapping(target = "employeeId", source = "employeeId")
    // Implicit mapping (List<IncidentMedia> --> List<IncidentMediaResponseDTO>)
    @Mapping(target = "medias", source = "medias", qualifiedByName = "noUrl")
    IncidentResponseDTO toResponse(Incident incident);

    /* Medias with readable urls. */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "citizenId", source = "citizenId")
    @Mapping(target = "employeeId", source = "employeeId")
    // Implicit mapping (List<IncidentMedia> --> List<IncidentMediaResponseDTO>)
    @Mapping(target = "medias", source = "medias", qualifiedByName = "withUrl")
    IncidentResponseDTO toResponse(Incident incident, @Context MediaLinkBuilder linkBuilder);

}

