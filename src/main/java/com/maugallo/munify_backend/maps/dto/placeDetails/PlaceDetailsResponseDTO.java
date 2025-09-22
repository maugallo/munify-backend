package com.maugallo.munify_backend.maps.dto.placeDetails;

public record PlaceDetailsResponseDTO(
    LatLng location,
    Address address,
    Viewport viewport
) {
    public record LatLng(double latitude, double longitude) { }
    public record Address(String formatted) {}
    public record Viewport(LatLng northeast, LatLng southwest) { }
}
