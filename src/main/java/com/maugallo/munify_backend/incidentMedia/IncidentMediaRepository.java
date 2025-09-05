package com.maugallo.munify_backend.incidentMedia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentMediaRepository extends JpaRepository<IncidentMedia, Long> {

    List<IncidentMedia> findByIncident_IdAndIncident_MunicipalityIdAndIsEnabledTrue(
            Long incidentId, Long municipalityId);

    Optional<IncidentMedia> findByIdAndIncident_MunicipalityIdAndIsEnabledTrue(
            Long id, Long municipalityId);

    Optional<IncidentMedia> findByStorageKeyAndIncident_MunicipalityId(
            String storageKey, Long municipalityId);

}
