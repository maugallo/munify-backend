package com.maugallo.munify_backend.employee;


import com.maugallo.munify_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<User, Long> {
    Optional<Employee> findByUsername(String username);
}
