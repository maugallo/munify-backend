package com.maugallo.munify_backend.municipality.dto;

import com.maugallo.munify_backend.municipalityFeature.dto.FeatureUpdateDTO;

import java.util.List;

public record MunicipalityFeaturesUpdateDTO(List<FeatureUpdateDTO> features) { }
