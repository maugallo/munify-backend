package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.incidentMedia.dto.abort.AbortRequestDTO;
import com.maugallo.munify_backend.incidentMedia.dto.prepare.IncidentMediaPrepareRequestDTO;
import com.maugallo.munify_backend.incidentMedia.dto.prepare.IncidentMediaPrepareResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/municipalities/{municipalityId}")
public class IncidentMediaController {

    private final IncidentMediaService incidentMediaService;

    public IncidentMediaController(IncidentMediaService incidentMediaService) {
        this.incidentMediaService = incidentMediaService;
    }

    @PostMapping("/incidents-media/prepare")
    public ResponseEntity<IncidentMediaPrepareResponseDTO> prepare(
            @PathVariable Long municipalityId,
            @Valid @RequestBody IncidentMediaPrepareRequestDTO medias
    ) {
        var preparedMedias = incidentMediaService.prepareUploads(municipalityId, medias);
        return ResponseEntity.status(HttpStatus.CREATED).body(preparedMedias);
    }

    @PostMapping("/incidents-media/abort")
    public ResponseEntity<Void> abort(
        @PathVariable Long municipalityId,
        @RequestBody AbortRequestDTO abortRequest
    ) {
        incidentMediaService.abortUpload(municipalityId, abortRequest.storageKey());
        return ResponseEntity.noContent().build();
    }

}
