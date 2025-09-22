package com.maugallo.munify_backend.maps.dto.autocomplete;

import java.util.List;

public record AutocompleteResponseDTO(
    List<SuggestionDTO> suggestions
) { }
