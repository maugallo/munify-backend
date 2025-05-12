package com.maugallo.munify_backend.employee;

import com.maugallo.munify_backend.municipality.Municipality;

public record EmployeeResponseDTO(Long id,

                                  String username,

                                  Municipality municipality,

                                  String role) {
}
