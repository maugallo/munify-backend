package com.maugallo.munify_backend.incidentMedia.dto.prepare;

public record IncidentMediaPrepareResponseDTO(
        String clientFileId,
        String storageKey,   // municipalities/{municipalityId}/staging/...
        PresignedUrlDTO presigned
) {
    public record PresignedUrlDTO(
            String url,
            String method,
            long   expiresAtSec,
            java.util.Map<String,String> requiredHeaders
    ) { }
}

