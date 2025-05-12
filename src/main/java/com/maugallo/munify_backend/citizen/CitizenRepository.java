package com.maugallo.munify_backend.citizen;

import com.maugallo.munify_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenRepository extends JpaRepository<User, Long> {
    Optional<Citizen> findByUsername(String username);
}
