package com.maugallo.munify_backend.incident.dto;

import com.maugallo.munify_backend.incident.IncidentStatus;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record IncidentResponseDTO(
    String id,
    String title,
    String description,
    IncidentStatus status,
    Double latitude,
    Double longitude,
    String address,
    List<IncidentMediaResponseDTO> medias,
    LocalDateTime updatedAt,
    String citizenId,
    String employeeId
) { }
