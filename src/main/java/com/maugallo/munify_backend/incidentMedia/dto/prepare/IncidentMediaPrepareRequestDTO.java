package com.maugallo.munify_backend.incidentMedia.dto.prepare;

import java.util.List;

public record IncidentMediaPrepareRequestDTO(
        List<IncidentMediaPrepareItemRequestDTO> items
) { }
