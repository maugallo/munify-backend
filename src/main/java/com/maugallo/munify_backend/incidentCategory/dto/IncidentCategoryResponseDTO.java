package com.maugallo.munify_backend.incidentCategory.dto;

import com.maugallo.munify_backend.incident.Incident;

import java.util.List;

public record IncidentCategoryResponseDTO(Long id,

                                          String name,

                                          List<Incident> incidents) {
}
