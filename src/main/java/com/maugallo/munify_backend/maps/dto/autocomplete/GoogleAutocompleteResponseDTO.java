package com.maugallo.munify_backend.maps.dto.autocomplete;

import java.util.List;

/** DTO que devuelve la API de Google */

public record GoogleAutocompleteResponseDTO(List<Suggestion> suggestions) {
    public record Suggestion(PlacePrediction placePrediction) {}
    public record PlacePrediction(String placeId, Text text, StructuredFormat structuredFormat) {}
    public record Text(String text) {}
    public record StructuredFormat(Part mainText, Part secondaryText) {}
    public record Part(String text) {}
}
