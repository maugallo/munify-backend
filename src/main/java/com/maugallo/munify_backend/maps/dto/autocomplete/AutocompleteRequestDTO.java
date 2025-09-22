package com.maugallo.munify_backend.maps.dto.autocomplete;

import jakarta.validation.constraints.NotBlank;

public record AutocompleteRequestDTO(
    @NotBlank String input,

    @NotBlank String sessionToken
){ }
