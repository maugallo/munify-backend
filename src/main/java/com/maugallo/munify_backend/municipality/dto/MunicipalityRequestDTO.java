package com.maugallo.munify_backend.municipality.dto;

public record MunicipalityRequestDTO (
        String name,
        String logoUrl,
        String primaryColor,
        String secondaryColor
) { }
