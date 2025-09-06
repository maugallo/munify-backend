package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import com.maugallo.munify_backend.incidentMedia.IncidentMediaLinkResolver;
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
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isEnabled", constant = "true")
    @Mapping(target = "municipalityId", source = "municipalityId")
    @Mapping(target = "citizenId", source = "citizenId")
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "medias", ignore = true) // las medias se asocian en el commit del servicio
    Incident toEntity(IncidentRequestDTO incident);

    /* Medias without readable urls. */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "citizenId", source = "citizenId")
    @Mapping(target = "employeeId", source = "employeeId")
    // Implicit mapping (List<IncidentMedia> --> List<IncidentMediaResponseDTO>)
    @Mapping(target = "medias", source = "medias")
    IncidentResponseDTO toDto(Incident incident);

    /* Medias with readable urls. */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "citizenId", source = "citizenId")
    @Mapping(target = "employeeId", source = "employeeId")
    // Implicit mapping (List<IncidentMedia> --> List<IncidentMediaResponseDTO>)
    @Mapping(target = "medias", source = "medias")
    IncidentResponseDTO toDto(Incident incident, @Context IncidentMediaLinkResolver linkResolver);

}

