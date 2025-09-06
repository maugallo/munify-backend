package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.exception.UnsupportedMediaTypeException;
import com.maugallo.munify_backend.incident.Incident;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaRequestDTO;
import com.maugallo.munify_backend.incidentMedia.dto.prepare.*;
import com.maugallo.munify_backend.media.KeyBuilder;
import com.maugallo.munify_backend.media.MediaStorage;
import com.maugallo.munify_backend.media.models.HeadObjectInfo;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class IncidentMediaService {

    private final MediaStorage mediaStorage;
    private final KeyBuilder keyBuilder;

    public IncidentMediaService(MediaStorage mediaStorage, KeyBuilder keyBuilder) {
        this.mediaStorage = mediaStorage;
        this.keyBuilder = keyBuilder;
    }

    public IncidentMediaPrepareResponseDTO prepareUploads(Long municipalityId, IncidentMediaPrepareRequestDTO medias) {
        var items = medias.items()
                .stream()
                .map(media -> {
                    var extension = getExtension(media.mime());
                    var storageKey = keyBuilder.forIncidentStaging(municipalityId, media.clientFileId(), extension);
                    var presignedPut = mediaStorage.presignPut(storageKey, media.mime());
                    var presignedUrl = new PresignedUrlDTO(
                            presignedPut.url(),
                            "PUT",
                            presignedPut.expiresAtSec(),
                            presignedPut.requiredHeaders()
                    );

                    return new IncidentMediaPrepareItemResponseDTO(media.clientFileId(), storageKey, presignedUrl);
                }).toList();

        return new IncidentMediaPrepareResponseDTO(items);
    }

    public IncidentMedia moveIncidentToCommit(IncidentMediaRequestDTO media, Incident incident, Long municipalityId) {
        // Validar HEAD del media en staging.
        var headStaging = mediaStorage.getHead(media.storageKey());
        validateHead(media, headStaging);

        // Copiar el objeto de staging/ a incident/.
        var extension = getExtension(media.mime());
        var sourceKey = media.storageKey();
        var destinationKey = keyBuilder.forIncidentPermanent(municipalityId, incident.getId(),
                UUID.randomUUID().toString(), extension);
        mediaStorage.copyObject(sourceKey, destinationKey);

        // Eliminar el objeto de staging/.
        mediaStorage.deleteObject(sourceKey);

        // Armar nuevo HEAD del media ya movido.
        var headCommit = mediaStorage.getHead(destinationKey);

        return IncidentMedia.builder()
                .incident(incident)
                .type(media.type())
                .mime(headCommit.contentType())
                .size(headCommit.contentLength())
                .etag(headCommit.eTag())
                .storageKey(destinationKey)
                .isEnabled(true)
                .build();
    }

    private static void validateHead(IncidentMediaRequestDTO media, HeadObjectInfo head) {
        if (!Objects.equals(media.mime(), head.contentType()))
            throw new IllegalStateException("Content-Type inesperado");
        if (!Objects.equals(media.size(), head.contentLength()))
            throw new IllegalStateException("Content-Length inesperado");
    }

    private static String getExtension(String mime) {
        if (mime == null || mime.isBlank()) return null;

        // limpiar parámetros (ej: "image/jpeg; charset=utf-8")
        int scIdx = mime.indexOf(';');
        String base = (scIdx >= 0 ? mime.substring(0, scIdx) : mime).trim().toLowerCase();

        return switch (base) {
            // imágenes
            case "image/jpeg", "image/jpg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            case "image/heic" -> "heic";
            case "image/heif" -> "heif";

            // videos
            case "video/mp4" -> "mp4";
            case "video/quicktime" -> "mov";
            case "video/3gpp" -> "3gp";
            case "video/webm" -> "webm";

            default -> throw new UnsupportedMediaTypeException("Tipo de archivo no soportado");
        };
    }

}
