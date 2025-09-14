package com.maugallo.munify_backend;

import com.maugallo.munify_backend.citizen.Citizen;
import com.maugallo.munify_backend.citizen.CitizenRepository;
import com.maugallo.munify_backend.municipality.Municipality;
import com.maugallo.munify_backend.municipality.MunicipalityRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Profile("dev")
@Component
public class DevBootstrap implements ApplicationRunner {

    private final MunicipalityRepository municipalityRepository;
    private final CitizenRepository citizenRepository;

    public DevBootstrap(MunicipalityRepository municipalityRepository, CitizenRepository citizenRepository) {
        this.municipalityRepository = municipalityRepository;
        this.citizenRepository = citizenRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        initMunicipality();
        initCitizen();
    }

    private void initMunicipality() {
        Boolean exists = municipalityRepository.existsByName("Test");
        if (!exists) {
            Municipality municipality = Municipality.builder()
                    .name("Test")
                    .logoUrl("...")
                    .primaryColor("")
                    .secondaryColor("")
                    .isEnabled(true)
                    .build();
            municipalityRepository.save(municipality);
            System.out.println("Municipality initialized");
        }
    }

    private void initCitizen() {
        Boolean exists = citizenRepository.existsByName("Test");
        if (!exists) {
            Citizen citizen = Citizen.builder()
                    .dni(UUID.randomUUID().toString())
                    .gender("h")
                    .selfieUrl("...")
                    .email("...")
                    .name("...")
                    .surname("...")
                    .address("...")
                    .zipCode("...")
                    .phone("...")
                    .birthDate(LocalDate.now())
                    .build();
            citizenRepository.save(citizen);
            System.out.println("Citizen initialized");
        }
    }

}
