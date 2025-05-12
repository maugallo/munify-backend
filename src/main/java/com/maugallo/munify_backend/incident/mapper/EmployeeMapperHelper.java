package com.maugallo.munify_backend.incident.mapper;

import com.maugallo.munify_backend.employee.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperHelper {

    private final EmployeeRepository employeeRepository;

    public EmployeeMapperHelper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
