package com.maugallo.munify_backend.incident;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    Optional<Incident> findByIdAndMunicipalityIdAndIsEnabledTrue(Long id, Long municipalityId);

    Optional<Incident> findByIdAndMunicipalityIdAndCitizenIdAndIsEnabledTrue(
            Long id, Long municipalityId, Long citizenId);

}
