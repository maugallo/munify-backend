package com.maugallo.munify_backend.incidentCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentCategoryRepository extends JpaRepository<IncidentCategory, Long> {

    List<IncidentCategory> findAllByIsEnabledTrue();

}
