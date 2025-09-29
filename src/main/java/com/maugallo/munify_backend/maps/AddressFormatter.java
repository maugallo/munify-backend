package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.maps.dto.geocoding.GoogleReverseResponseDTO;

public final class AddressFormatter {

    private AddressFormatter() {}

    public static String friendlyFromReverse(GoogleReverseResponseDTO reverse) {
        var result = (reverse != null && reverse.results() != null && !reverse.results().isEmpty())
                ? reverse.results().getFirst() : null;
        if (result == null) return "Ubicación sin nombre";

        String route = null, number = null, locality = null, neighborhood = null, sublocality = null, province = null;

        // --- address_components (si lo tenés en tu DTO extendido) ---
        var comps = result.address_components();
        if (comps != null) {
            for (var c : comps) {
                var types = c.types();
                if (types == null) continue;

                String shortName = safe(c.short_name());
                String longName  = safe(c.long_name());
                String name = !shortName.isBlank() ? shortName : longName;
                if (name.isBlank()) continue;

                if (types.contains("postal_code")) continue; // ignorar CPA
                if (types.contains("route")) route = name;
                else if (types.contains("street_number")) number = name;
                else if (types.contains("locality")) locality = name;
                else if (types.contains("neighborhood")) neighborhood = name;
                else if (types.contains("sublocality") || types.contains("sublocality_level_1")) sublocality = name;
                else if (types.contains("administrative_area_level_1")) province = name; // Provincia
            }
        }

        // --- 1) Calle + número + localidad
        if (nonBlank(route) && nonBlank(number) && nonBlank(locality)) {
            return fmt("%s %s, %s", route, number, locality);
        }
        // --- 2) Calle + localidad
        if (nonBlank(route) && nonBlank(locality)) {
            return fmt("%s, %s", route, locality);
        }

        // Plus code (corto si hay compound_code; si no, global_code)
        String plus = shortPlusCode(reverse);

        // --- 3) PlusCode + Barrio/Sublocalidad + Localidad (si hay área y localidad)
        String area = nonBlank(neighborhood) ? neighborhood : sublocality;
        if (nonBlank(plus) && nonBlank(area) && nonBlank(locality)) {
            return fmt("%s, %s, %s", plus, area, locality);
        }

        // --- 4) PlusCode + Localidad
        if (nonBlank(plus) && nonBlank(locality)) {
            return fmt("%s, %s", plus, locality);
        }

        // --- 5) PlusCode + Provincia (cuando no hay localidad)
        if (nonBlank(plus) && nonBlank(province)) {
            return fmt("%s, %s", plus, cleanProvince(province));
        }

        // --- 6) Fallback: formatted sin país y limpiando CPA al final
        String formatted = safe(result.formatted_address());
        if (!formatted.isBlank()) {
            formatted = formatted
                    .replaceAll(",?\\s*Argentina$", "")               // quitar país
                    .replaceFirst("^Provincia de\\s+", "")            // opcional: “Provincia de …” -> “…”
                    .replaceAll(",?\\s*[A-Z]\\d{4}[A-Z]{3}$", "")     // quitar CPA tipo B2812DBH al final
                    .replaceAll("\\s*,\\s*", ", ")
                    .replaceAll("\\s{2,}", " ")
                    .trim();
            return formatted.isBlank() ? "Ubicación sin nombre" : formatted;
        }

        return "Ubicación sin nombre";
    }

    // ----------------- helpers -----------------

    private static String shortPlusCode(GoogleReverseResponseDTO reverse) {
        if (reverse == null || reverse.plus_code() == null) return "";
        var pc = reverse.plus_code();

        // Preferir compound_code: "HW5+34M Capilla del Señor, Provincia de ..."
        String compound = safe(pc.compound_code());
        if (!compound.isBlank()) {
            String left = compound.split(",", 2)[0].trim();   // "HW5+34M Capilla del Señor"
            String[] tokens = left.split("\\s+", 2);
            if (tokens.length >= 1) {
                // tokens[0] -> "HW5+34M" (corto)
                return tokens[0].trim();
            }
        }

        // Si no hay compound, usar global_code (largo): "849VCWC8+R9"
        return safe(pc.global_code());
    }

    private static boolean nonBlank(String s) { return s != null && !s.isBlank(); }
    private static String safe(String s) { return s == null ? "" : s; }
    private static String fmt(String tpl, Object... args) {
        return String.format(tpl, args).replaceAll("\\s*,\\s*", ", ").replaceAll("\\s{2,}", " ").trim();
    }
    private static String cleanProvince(String province) {
        return safe(province).replaceFirst("^Provincia de\\s+", "").trim();
    }
}
