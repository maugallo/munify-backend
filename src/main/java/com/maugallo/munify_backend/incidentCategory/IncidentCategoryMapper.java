package com.maugallo.munify_backend.incidentCategory;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryRequestDTO;
import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface IncidentCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "isEnabled", constant = "true")
    @Mapping(target = "incidents", ignore = true)
    IncidentCategory toEntity(IncidentCategoryRequestDTO category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    IncidentCategoryResponseDTO toResponse(IncidentCategory category);

}
