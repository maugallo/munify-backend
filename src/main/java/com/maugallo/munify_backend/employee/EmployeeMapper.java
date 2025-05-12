package com.maugallo.munify_backend.employee;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO employee);

    EmployeeResponseDTO toResponse(Employee employee);

}
