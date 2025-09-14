package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/municipalities/{municipalityId}/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping()
    public ResponseEntity<IncidentResponseDTO> createIncident(
            @PathVariable Long municipalityId,
            @Valid @RequestBody IncidentRequestDTO body
    ) {
        var responseDto = incidentService.createIncident(municipalityId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping()
    public ResponseEntity<List<IncidentResponseDTO>> getIncidents(@PathVariable Long municipalityId) {
        var incidents = incidentService.getIncidents(municipalityId);
        return ResponseEntity.status(HttpStatus.OK).body(incidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponseDTO> getIncident(
            @PathVariable Long municipalityId,
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean includeMediaUrls
    ) {
        return ResponseEntity.ok(incidentService.getIncident(id, municipalityId, includeMediaUrls));
    }

    @GetMapping("/categories")
    public ResponseEntity<IncidentCategory[]> getIncidentCategories() {
        return ResponseEntity.ok(IncidentCategory.values());
    }

}
