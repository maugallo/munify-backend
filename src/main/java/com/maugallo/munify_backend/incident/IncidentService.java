package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import com.maugallo.munify_backend.incidentMedia.IncidentMedia;
import com.maugallo.munify_backend.incidentMedia.IncidentMediaRepository;
import com.maugallo.munify_backend.incidentMedia.IncidentMediaService;
import com.maugallo.munify_backend.media.MediaStorage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMediaRepository incidentMediaRepository;
    private final IncidentMediaService incidentMediaService;
    private final IncidentMapper incidentMapper;
    private final MediaStorage mediaStorage;

    public IncidentService(IncidentRepository incidentRepository,
                           IncidentMediaRepository incidentMediaRepository,
                           IncidentMediaService incidentMediaService,
                           IncidentMapper incidentMapper,
                           MediaStorage mediaStorage
    ) {
        this.incidentRepository = incidentRepository;
        this.incidentMediaRepository = incidentMediaRepository;
        this.incidentMediaService = incidentMediaService;
        this.incidentMapper = incidentMapper;
        this.mediaStorage = mediaStorage;
    }

    @Transactional
    public IncidentResponseDTO createIncident(Long municipalityId, @Valid IncidentRequestDTO incidentRequest) {
        if (!String.valueOf(municipalityId).equals(incidentRequest.municipalityId()))
            throw new AccessDeniedException("ID de municipio inválido");

        var incident = incidentRepository.saveAndFlush(incidentMapper.toEntity(incidentRequest));

        var persistedMedias = new ArrayList<IncidentMedia>();
        try {
            incidentRequest.medias().forEach(media -> {
                var mediaEntity = incidentMediaService.moveIncidentToCommit(media, incident, municipalityId);
                persistedMedias.add(mediaEntity);
            });
            incidentMediaRepository.saveAllAndFlush(persistedMedias);
            incident.setMedias(persistedMedias);

            return incidentMapper.toDto(incident);
        } catch (RuntimeException ex) {
            persistedMedias.forEach(media -> safeDelete(media.getStorageKey()));
            throw ex;
        }
    }

    private void safeDelete(String key) {
        try { mediaStorage.deleteObject(key); } catch (Exception ignore) {}
    }

}
