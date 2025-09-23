package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.maps.dto.geocoding.GoogleReverseResponseDTO;

public final class AddressFormatter {

    public static String friendlyFromReverse(GoogleReverseResponseDTO reverse) {
        var result = reverse.results().getFirst();
        if (result == null) return "Ubicación sin nombre";

        var addressComponents = result.address_components();
        String route = null, number = null, locality = null, neighborhood = null, sublocality = null;

        if (addressComponents != null && !addressComponents.isEmpty()) {
            for (var component : addressComponents) {
                var types = component.types();

                String name = !component.short_name().isEmpty() ? component.short_name() : component.long_name();
                if (types == null || name == null) continue;
                if (types.contains("route")) route = name;
                if (types.contains("street_number")) number = name;
                if (types.contains("locality")) locality = name;
                if (types.contains("neighborhood")) neighborhood = name;
                if (types.contains("sublocality") || types.contains("sublocality_level_1")) sublocality = name;
            }
        }

        if (route != null && number != null && locality != null) {
            return route + " " + number + ", " + locality;
        }
        if (route != null && locality != null) {
            return route + ", " + locality;
        }

        String area = (neighborhood != null) ? neighborhood : sublocality;
        if (area != null && locality != null) {
            return area + ", " + locality;
        }

        if (locality != null) {
            return locality;
        }

        // Fallback
        var plusCode = reverse.plus_code();
        if (plusCode != null) {
            String compound = plusCode.compound_code();
            if (compound != null && !compound.isBlank()) {
                // Tomar "HW5+34M" y la primera parte de la localidad
                String[] parts = compound.split(",", 2);
                String left = parts[0]; // "HW5+34M Capilla del Señor"
                // separar código + localidad: código es primer token
                String[] tokens = left.split("\\s+", 2);
                if (tokens.length == 2) {
                    String code = tokens[0];       // "HW5+34M"
                    String loc = tokens[1];       // "Capilla del Señor"
                    return code + ", " + loc;      // opción A (recomendada)
                }
            }
        }

        String formatted = result.formatted_address();
        if (formatted != null && !formatted.isBlank()) {
            return formatted
                    // saca ", Provincia de XXX, Argentina"
                    .replaceAll(",?\\s*Provincia de [^,]+,?\\s*Argentina$", "")
                    // saca ", XXX, Argentina"
                    .replaceAll(",?\\s*[^,]+,?\\s*Argentina$", "")
                    // saca ", Argentina" a secas
                    .replaceAll(",?\\s*Argentina$", "")
                    .trim();
        }

        return "Ubicación sin nombre";
    }
}
