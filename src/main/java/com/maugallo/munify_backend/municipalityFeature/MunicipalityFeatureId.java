package com.maugallo.munify_backend.municipalityFeature;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/* Definición de PK compuesta. */

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalityFeatureId implements Serializable {

    private Long municipalityId;

    private Long featureId;

}
