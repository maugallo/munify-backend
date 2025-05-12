package com.maugallo.munify_backend.citizen;

import com.maugallo.munify_backend.municipality.Municipality;

import java.time.LocalDate;

public record CitizenResponseDTO(Long id,

                                 String dni,

                                 String gender,

                                 String selfieUrl,

                                 String email,

                                 String name,

                                 String surname,

                                 String address,

                                 String zipCode,

                                 String phone,

                                 LocalDate birthDate,

                                 String username,

                                 Municipality municipality) {
}
