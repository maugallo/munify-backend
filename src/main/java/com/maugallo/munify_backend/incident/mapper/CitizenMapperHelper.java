package com.maugallo.munify_backend.incident.mapper;

import com.maugallo.munify_backend.citizen.CitizenRepository;
import org.springframework.stereotype.Component;

@Component
public class CitizenMapperHelper {

    private final CitizenRepository citizenRepository;

    public CitizenMapperHelper(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

}

