package com.maugallo.munify_backend.incidentMedia.dto.prepare;

public record IncidentMediaPrepareResponseDTO(
        String clientFileId,
        String storageKey,   // municipalities/{municipalityId}/staging/...
        PresignedUrlDTO presigned
) { }

