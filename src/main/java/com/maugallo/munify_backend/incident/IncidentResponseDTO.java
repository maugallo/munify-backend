package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.citizen.CitizenResponseDTO;
import com.maugallo.munify_backend.employee.EmployeeResponseDTO;
import com.maugallo.munify_backend.incidentCategory.IncidentCategoryResponseDTO;
import com.maugallo.munify_backend.incidentMedia.IncidentMediaResponseDTO;
import com.maugallo.munify_backend.municipality.Municipality;

import java.util.List;

public record IncidentResponseDTO(Long id,

                                  String description,

                                  String status,

                                  Double latitude,

                                  Double longitude,

                                  Municipality municipality,

                                  CitizenResponseDTO citizen,

                                  EmployeeResponseDTO employee,

                                  IncidentCategoryResponseDTO category,

                                  List<IncidentMediaResponseDTO> medias) {
}
