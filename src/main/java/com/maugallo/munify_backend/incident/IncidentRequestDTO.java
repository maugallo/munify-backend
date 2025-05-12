package com.maugallo.munify_backend.incident;

import com.maugallo.munify_backend.incidentMedia.IncidentMediaRequestDTO;
import com.maugallo.munify_backend.municipality.Municipality;
import com.maugallo.munify_backend.validation.EnumValidator;

import java.util.List;

public record IncidentRequestDTO(String description,

                                 @EnumValidator(enumClass = IncidentStatus.class)
                                 String status,

                                 Double latitude,

                                 Double longitude,

                                 Municipality municipality,

                                 Long citizenId,

                                 Long employeeId,

                                 Long categoryId,

                                 List<IncidentMediaRequestDTO> medias) {
}
