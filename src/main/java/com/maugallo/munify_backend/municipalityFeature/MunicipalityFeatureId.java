package com.maugallo.munify_backend.municipalityFeature;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/* Definición de PK compuesta. */

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalityFeatureId implements Serializable {

    private Long municipalityId;

    private Long featureId;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        MunicipalityFeatureId that = (MunicipalityFeatureId) object;
        return Objects.equals(municipalityId, that.municipalityId) &&
                Objects.equals(featureId, that.featureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(municipalityId, featureId);
    }

}
