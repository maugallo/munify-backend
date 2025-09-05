package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.incidentMedia.dto.ReadUrlDTO;

public interface IncidentMediaLinkResolver {
    ReadUrlDTO resolve(IncidentMedia incidentMedia);
}
