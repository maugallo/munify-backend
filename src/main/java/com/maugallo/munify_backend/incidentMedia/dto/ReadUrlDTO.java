package com.maugallo.munify_backend.incidentMedia.dto;

public record ReadUrlDTO(
    String readUrl,     // presignado GET efímero
    long   expiresAtSec // epoch seg hasta que venza
) { }
