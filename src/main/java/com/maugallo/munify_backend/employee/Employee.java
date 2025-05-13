package com.maugallo.munify_backend.employee;

import com.maugallo.munify_backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends User {

    @Column
    private String role;

}
