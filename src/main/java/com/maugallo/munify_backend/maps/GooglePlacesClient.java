package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.maps.dto.autocomplete.AutocompleteRequestDTO;
import com.maugallo.munify_backend.maps.dto.autocomplete.GoogleAutocompleteResponseDTO;
import com.maugallo.munify_backend.maps.dto.placeDetails.GooglePlaceDetailsResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class GooglePlacesClient {

    private final RestClient restClient;

    public GooglePlacesClient(@Qualifier("placesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public GoogleAutocompleteResponseDTO autocomplete(AutocompleteRequestDTO request) {
        var body = Map.of(
        "input", request.input(),
        "sessionToken", request.sessionToken(),
        "includedRegionCodes", List.of("AR"),
        "languageCode", "es-AR",
        "regionCode", "AR"
        );

        return restClient.post()
                .uri("/places:autocomplete")
                .header("X-Goog-FieldMask",
                        "suggestions.placePrediction.placeId," +
                                "suggestions.placePrediction.text," +
                                "suggestions.placePrediction.structuredFormat.mainText," +
                                "suggestions.placePrediction.structuredFormat.secondaryText")
                .body(body)
                .retrieve()
                .body(GoogleAutocompleteResponseDTO.class);
    }

    public GooglePlaceDetailsResponseDTO placeDetails(String placeId, String sessionToken) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/places/{placeId}")
                        .queryParam("languageCode", "es-AR")
                        .queryParam("regionCode", "AR")
                        .build(placeId))
                .header("X-Goog-FieldMask", "id,formattedAddress,location,viewport")
                .header("X-Goog-Session-Token", sessionToken)
                .retrieve()
                .body(GooglePlaceDetailsResponseDTO.class);
    }

}
