package com.maugallo.munify_backend.citizen;

import com.maugallo.munify_backend.municipality.Municipality;

import java.time.LocalDate;

public record CitizenRequestDTO(String username,

                                String password,

                                Municipality municipality,

                                String gender,

                                String selfieUrl,

                                String email,

                                String name,

                                String surname,

                                String address,

                                String zipCode,

                                String phone,

                                LocalDate birthDate) {
}
