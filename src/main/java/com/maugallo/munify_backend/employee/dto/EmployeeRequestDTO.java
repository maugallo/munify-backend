package com.maugallo.munify_backend.employee.dto;

import com.maugallo.munify_backend.employee.EmployeeRole;
import com.maugallo.munify_backend.municipality.Municipality;
import com.maugallo.munify_backend.validation.EnumValidator;

public record EmployeeRequestDTO(String username,

                                 String password,

                                 Municipality municipality,

                                 @EnumValidator(enumClass = EmployeeRole.class)
                                 String role) { }
