package com.maugallo.munify_backend.incident.dto;

import com.maugallo.munify_backend.citizen.dto.CitizenResponseDTO;
import com.maugallo.munify_backend.employee.dto.EmployeeResponseDTO;
import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryResponseDTO;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaResponseDTO;
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
