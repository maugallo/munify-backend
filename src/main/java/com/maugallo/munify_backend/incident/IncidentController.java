package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/municipalities/{municipalityId}/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    public IncidentController(IncidentService incidentService, IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentService = incidentService;
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }

    @PostMapping()
    public ResponseEntity<IncidentResponseDTO> createIncident(
            @PathVariable Long municipalityId,
            @Valid @RequestBody IncidentRequestDTO body
    ) {
        var responseDto = incidentService.createIncident(municipalityId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
