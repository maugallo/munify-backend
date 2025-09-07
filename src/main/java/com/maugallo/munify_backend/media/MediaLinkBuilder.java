package com.maugallo.munify_backend.media;

import com.maugallo.munify_backend.incidentMedia.dto.ReadUrlDTO;

public interface MediaLinkBuilder {
    ReadUrlDTO buildTemporalUrl(String storageKey);
    ReadUrlDTO buildPermanentUrl(String storageKey);
}
