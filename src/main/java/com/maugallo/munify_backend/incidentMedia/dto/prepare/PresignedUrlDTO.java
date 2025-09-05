package com.maugallo.munify_backend.incidentMedia.dto.prepare;

public record PresignedUrlDTO(
        String url,                       // URL de PUT
        String method,                    // "PUT"
        long   expiresAtSec,              // epoch seconds
        java.util.Map<String,String> requiredHeaders
) { }
