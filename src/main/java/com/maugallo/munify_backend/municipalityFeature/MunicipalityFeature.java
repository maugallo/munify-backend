package com.maugallo.munify_backend.municipalityFeature;

import com.maugallo.munify_backend.feature.Feature;
import com.maugallo.munify_backend.municipality.Municipality;
import jakarta.persistence.*;

@Entity
public class MunicipalityFeature {

    @EmbeddedId
    private MunicipalityFeatureId id;

    @Column
    private Boolean isEnabled;

    @ManyToOne
    @MapsId("municipalityId")
    private Municipality municipality;

    @ManyToOne
    @MapsId("featureId")
    private Feature feature;

}
