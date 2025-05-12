package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.incident.Incident;

public record IncidentMediaResponseDTO(Long id,

                                       String type,

                                       String url,

                                       Incident incident) {
}
