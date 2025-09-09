package com.maugallo.munify_backend.citizen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    Boolean existsByName(String name);

    Optional<Citizen> findByUsername(String username);

}
