package com.maugallo.munify_backend.municipality.dto;

import com.maugallo.munify_backend.municipalityFeature.dto.MunicipalityFeatureResponseDTO;

import java.util.List;

public record MunicipalityResponseDTO(
    Long id,
    String name,
    String logoUrl,
    String primaryColor,
    String secondaryColor,
    List<MunicipalityFeatureResponseDTO> features
) { }
