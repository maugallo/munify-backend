package com.maugallo.munify_backend.municipality;

import com.maugallo.munify_backend.municipalityFeature.MunicipalityFeature;

import java.util.List;

public record MunicipalityResponseDTO(Long id,

                                      String name,

                                      String logoUrl,

                                      String primaryColor,

                                      String secondaryColor,

                                      List<MunicipalityFeature> features) {
}
