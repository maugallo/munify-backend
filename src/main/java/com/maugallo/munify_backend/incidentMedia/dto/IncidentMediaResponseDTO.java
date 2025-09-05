package com.maugallo.munify_backend.incidentMedia.dto;

import com.maugallo.munify_backend.incidentMedia.IncidentMediaType;

public record IncidentMediaResponseDTO(
    String id,
    IncidentMediaType type,
    String mime,
    long size,
    String storageKey,
    ReadUrlDTO url // puede venir null si no se solicitó
) { }
