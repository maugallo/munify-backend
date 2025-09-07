package com.maugallo.munify_backend.incidentMedia.dto.abort;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AbortRequestDTO(
    @NotBlank @Pattern(regexp="^municipalities/\\d+/staging/.+$")
    String storageKey
) { }
