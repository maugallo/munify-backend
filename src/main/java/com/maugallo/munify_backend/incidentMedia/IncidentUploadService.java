package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.incidentMedia.dto.prepare.*;
import com.maugallo.munify_backend.media.KeyBuilder;
import com.maugallo.munify_backend.media.MediaStorage;
import org.springframework.stereotype.Service;

@Service
public class IncidentUploadService {

    private final MediaStorage mediaStorage;
    private final KeyBuilder keyBuilder;

    public IncidentUploadService(MediaStorage mediaStorage, KeyBuilder keyBuilder) {
        this.mediaStorage = mediaStorage;
        this.keyBuilder = keyBuilder;
    }

    public IncidentMediaPrepareResponseDTO prepareUploads(Long municipalityId, IncidentMediaPrepareRequestDTO medias) {
        var items = medias.items()
                .stream()
                .map(media -> {
                    var storageKey = keyBuilder.forIncidentStaging(municipalityId, media.clientFileId(), media.mime());
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

}
