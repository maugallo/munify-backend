package com.maugallo.munify_backend.maps.dto.geocoding;

import java.util.List;

/** DTO que devuelve la API de Google */

public record GoogleReverseResponseDTO(
        PlusCode plus_code,
        List<Result> results,
        String status
) {
    public record PlusCode(
            String compound_code,
            String global_code
    ) {}

    public record Result(
            String formatted_address,
            String place_id,
            List<AddressComponent> address_components
    ) {}

    public record AddressComponent(
            String long_name,
            String short_name,
            List<String> types
    ) {}
}

