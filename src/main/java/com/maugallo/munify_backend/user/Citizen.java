package com.maugallo.munify_backend.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Citizen extends User {

    @Column(unique = true)
    private String dni;

    @Column
    private String gender;

    @Column
    private String selfieUrl;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String address;

    @Column
    private String zipCode;

    @Column
    private String phone;

    @Column
    private LocalDate birthDate;

}
