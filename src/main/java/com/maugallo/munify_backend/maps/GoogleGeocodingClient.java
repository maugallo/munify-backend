package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.maps.dto.geocoding.GoogleReverseResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GoogleGeocodingClient {

    private final RestClient restClient;
    private final GoogleMapsProps props;

    public GoogleGeocodingClient(@Qualifier("geocodingRestClient") RestClient restClient, GoogleMapsProps props) {
        this.restClient = restClient;
        this.props = props;
    }

    public GoogleReverseResponseDTO reverse(double lat, double lng) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/json")
                        .queryParam("latlng", lat + "," + lng)
                        .queryParam("key", props.apiKey())
                        .build())
                .retrieve()
                .body(GoogleReverseResponseDTO.class);
    }

}
