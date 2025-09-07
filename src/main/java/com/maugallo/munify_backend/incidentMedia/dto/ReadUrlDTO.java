package com.maugallo.munify_backend.incidentMedia.dto;

public record ReadUrlDTO(
    String readUrl,
    Long expiresAtSec
) { }
