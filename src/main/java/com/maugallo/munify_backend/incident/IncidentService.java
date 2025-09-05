package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.incident.dto.IncidentRequestDTO;
import com.maugallo.munify_backend.incident.dto.IncidentResponseDTO;
import com.maugallo.munify_backend.incidentMedia.IncidentMedia;
import com.maugallo.munify_backend.incidentMedia.IncidentMediaRepository;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaRequestDTO;
import com.maugallo.munify_backend.media.KeyBuilder;
import com.maugallo.munify_backend.media.MediaStorage;
import com.maugallo.munify_backend.media.models.HeadObjectInfo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMediaRepository incidentMediaRepository;
    private final IncidentMapper incidentMapper;
    private final MediaStorage mediaStorage;
    private final KeyBuilder keyBuilder;


    public IncidentService(IncidentRepository incidentRepository,
                           IncidentMediaRepository incidentMediaRepository,
                           IncidentMapper incidentMapper,
                           MediaStorage mediaStorage,
                           KeyBuilder keyBuilder
    ) {
        this.incidentRepository = incidentRepository;
        this.incidentMediaRepository = incidentMediaRepository;
        this.incidentMapper = incidentMapper;
        this.mediaStorage = mediaStorage;
        this.keyBuilder = keyBuilder;
    }

    @Transactional
    public IncidentResponseDTO createIncident(Long municipalityId, Long citizenId, @Valid IncidentRequestDTO incidentRequest) {
        if (!String.valueOf(municipalityId).equals(incidentRequest.municipalityId()))
            throw new AccessDeniedException("ID de municipio inválido");
        if (!String.valueOf(citizenId).equals(incidentRequest.citizenId()))
            throw new AccessDeniedException("ID de ciudadano inválido");

        var incident = incidentRepository.saveAndFlush(incidentMapper.toEntity(incidentRequest));

        var persistedMedias = new ArrayList<IncidentMedia>();
        try {
            incidentRequest.medias().forEach(media -> {

                var headStaging = mediaStorage.head(media.storageKey());
                validateHeader(media, headStaging);

                var extension = extractExtension(media.storageKey(), media.mime());
                var destinationKey = keyBuilder.forIncidentPermanent(municipalityId, incident.getId(), UUID.randomUUID().toString(), extension);
                mediaStorage.copyObject(media.storageKey(), destinationKey);

                var finalHeader = mediaStorage.head(destinationKey);

                var mediaEntity = IncidentMedia.builder()
                        .incident(incident)
                        .type(media.type())
                        .mime(finalHeader.contentType())
                        .size(finalHeader.contentLength())
                        .etag(finalHeader.eTag())
                        .storageKey(destinationKey)
                        .isEnabled(true)
                        .build();
                persistedMedias.add(mediaEntity);
            });
            incidentMediaRepository.saveAll(persistedMedias);

            return incidentMapper.toDto(incident);
        } catch (RuntimeException ex) {
            persistedMedias.forEach(media -> safeDelete(media.getStorageKey()));
            throw ex;
        }
    }

    private static void validateHeader(IncidentMediaRequestDTO mediaRequest, HeadObjectInfo header) {
        if (!Objects.equals(mediaRequest.mime(), header.contentType()))
            throw new IllegalStateException("Content-Type inesperado");
        if (!Objects.equals(mediaRequest.size(), header.contentLength()))
            throw new IllegalStateException("Content-Length inesperado");
    }

    private static String extractExtension(String storageKey, String mime) {
        var dot = storageKey.lastIndexOf('.');
        if (dot > 0) return storageKey.substring(dot+1).toLowerCase();
        return switch (mime) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            case "image/heic" -> "heic";
            case "video/mp4" -> "mp4";
            case "video/quicktime" -> "mov";
            default -> "bin";
        };
    }

    private void safeDelete(String key) {
        try { mediaStorage.deleteObject(key); } catch (Exception ignore) {}
    }

}
