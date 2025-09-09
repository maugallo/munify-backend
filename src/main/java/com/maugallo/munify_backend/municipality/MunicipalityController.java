package com.maugallo.munify_backend.municipality;

import com.maugallo.munify_backend.municipality.dto.MunicipalityResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/municipalities")
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @GetMapping()
    public ResponseEntity<List<MunicipalityResponseDTO>> getMunicipalities() {
        return ResponseEntity.ok(municipalityService.getMunicipalities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipalityResponseDTO> getIncident(@PathVariable Long id) {
        return ResponseEntity.ok(municipalityService.getMunicipality(id));
    }

}
