package com.maugallo.munify_backend.incident.mapper;

import com.maugallo.munify_backend.incident.*;
import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        CitizenMapperHelper.class,
        EmployeeMapperHelper.class,
        CategoryMapperHelper.class })
public interface IncidentMapper {

    @Mapping(target = "citizen", source = "citizenId")
    @Mapping(target = "employee", source = "employeeId")
    @Mapping(target = "category", source = "categoryId")
    Incident toEntity(IncidentRequestDTO incident);

    IncidentResponseDTO toDto(Incident incident);

}

