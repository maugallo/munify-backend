package com.maugallo.munify_backend.municipality;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    Boolean existsByName(String name);

    List<Municipality> findAllByIsEnabledTrue();

    Optional<Municipality> findByIdAndIsEnabledTrue(Long id);

}
