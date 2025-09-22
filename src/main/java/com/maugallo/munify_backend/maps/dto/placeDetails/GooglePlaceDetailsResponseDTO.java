package com.maugallo.munify_backend.maps.dto.placeDetails;

/** DTO que devuelve la API de Google */

public record GooglePlaceDetailsResponseDTO(
        String id,
        String formattedAddress,
        LatLng location,
        Viewport viewport
) {
    public record LatLng(double latitude, double longitude) {}
    public record Viewport(LatLng low, LatLng high) {}
}
