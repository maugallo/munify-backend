package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.validation.EnumValidator;

public record IncidentMediaRequestDTO(String url,

                                      @EnumValidator(enumClass = IncidentMediaType.class)
                                      String type,

                                      Long incidentId) {
}
