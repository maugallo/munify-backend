package com.maugallo.munify_backend.incidentCategory;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidentCategoryMapper {

    IncidentCategory toEntity(IncidentCategoryRequestDTO category);

    IncidentCategoryResponseDTO toDto(IncidentCategory category);

}
