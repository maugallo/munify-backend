package com.maugallo.munify_backend.incident;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findAllByMunicipalityIdAndIsEnabledTrue(Long municipalityId);

    Optional<Incident> findByIdAndMunicipalityIdAndIsEnabledTrue(Long id, Long municipalityId);

    Optional<Incident> findByIdAndMunicipalityIdAndCitizenIdAndIsEnabledTrue(
            Long id, Long municipalityId, Long citizenId);

}
