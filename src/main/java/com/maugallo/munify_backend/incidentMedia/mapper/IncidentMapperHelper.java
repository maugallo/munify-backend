package com.maugallo.munify_backend.incidentMedia.mapper;

import com.maugallo.munify_backend.incidentMedia.IncidentMediaRepository;
import org.springframework.stereotype.Component;

@Component
public class IncidentMapperHelper {

    private final IncidentMediaRepository incidentMediaRepository;

    public IncidentMapperHelper(IncidentMediaRepository incidentMediaRepository) {
        this.incidentMediaRepository = incidentMediaRepository;
    }

}
