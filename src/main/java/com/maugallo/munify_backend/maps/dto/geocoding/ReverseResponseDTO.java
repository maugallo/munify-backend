package com.maugallo.munify_backend.maps.dto.geocoding;

public record ReverseResponseDTO(
        Address address
) {
    public record Address(String formatted) {}
}
