package com.maugallo.munify_backend.municipalityFeature;

import com.maugallo.munify_backend.feature.Feature;
import com.maugallo.munify_backend.municipality.Municipality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
