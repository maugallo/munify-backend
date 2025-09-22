package com.maugallo.munify_backend.maps.dto.geocoding;

import java.util.List;

/** DTO que devuelve la API de Google */

public record GoogleReverseResponseDTO(
        List<Result> results,
        String status
) {
    public record Result(String formatted_address, String place_id) {}
}
