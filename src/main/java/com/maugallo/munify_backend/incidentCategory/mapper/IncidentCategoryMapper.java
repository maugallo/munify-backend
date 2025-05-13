package com.maugallo.munify_backend.incidentCategory.mapper;

import com.maugallo.munify_backend.incidentCategory.IncidentCategory;
import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryRequestDTO;
import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidentCategoryMapper {

    IncidentCategory toEntity(IncidentCategoryRequestDTO category);

    IncidentCategoryResponseDTO toDto(IncidentCategory category);

}
