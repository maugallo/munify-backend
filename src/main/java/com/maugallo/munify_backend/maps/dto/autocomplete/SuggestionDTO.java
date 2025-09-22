package com.maugallo.munify_backend.maps.dto.autocomplete;

import jakarta.validation.constraints.NotBlank;

public record SuggestionDTO(
        @NotBlank String placeId,
        @NotBlank String primary,
        String secondary
) { }
