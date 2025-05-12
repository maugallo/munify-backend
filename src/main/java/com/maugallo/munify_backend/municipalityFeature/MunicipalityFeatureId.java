package com.maugallo.munify_backend.municipalityFeature;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

/* Definición de PK compuesta. */

@Embeddable
public class MunicipalityFeatureId implements Serializable {

    private Long municipalityId;

    private Long featureId;

}
