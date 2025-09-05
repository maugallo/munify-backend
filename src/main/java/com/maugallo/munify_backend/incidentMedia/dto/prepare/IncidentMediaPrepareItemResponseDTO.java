package com.maugallo.munify_backend.incidentMedia.dto.prepare;

public record IncidentMediaPrepareItemResponseDTO(
        String clientFileId,
        String storageKey,   // municipalities/{municipalityId}/staging/...
        PresignedUrlDTO presigned
) { }
