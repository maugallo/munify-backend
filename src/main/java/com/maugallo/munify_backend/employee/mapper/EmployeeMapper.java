package com.maugallo.munify_backend.employee.mapper;

import com.maugallo.munify_backend.employee.Employee;
import com.maugallo.munify_backend.employee.dto.EmployeeRequestDTO;
import com.maugallo.munify_backend.employee.dto.EmployeeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO employee);

    EmployeeResponseDTO toResponse(Employee employee);

}
