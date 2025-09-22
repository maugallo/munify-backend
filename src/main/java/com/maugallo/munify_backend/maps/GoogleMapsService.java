package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.maps.dto.autocomplete.AutocompleteRequestDTO;
import com.maugallo.munify_backend.maps.dto.autocomplete.AutocompleteResponseDTO;
import com.maugallo.munify_backend.maps.dto.autocomplete.GoogleAutocompleteResponseDTO;
import com.maugallo.munify_backend.maps.dto.geocoding.GoogleReverseResponseDTO;
import com.maugallo.munify_backend.maps.dto.geocoding.ReverseResponseDTO;
import com.maugallo.munify_backend.maps.dto.placeDetails.GooglePlaceDetailsResponseDTO;
import com.maugallo.munify_backend.maps.dto.placeDetails.PlaceDetailsResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

    private final GooglePlacesClient googlePlacesClient;
    private final GoogleGeocodingClient googleGeocodingClient;
    private final GoogleMapsMapper googleMapsMapper;

    public GoogleMapsService(GooglePlacesClient googlePlacesClient, GoogleGeocodingClient googleGeocodingClient, GoogleMapsMapper googleMapsMapper) {
        this.googlePlacesClient = googlePlacesClient;
        this.googleGeocodingClient = googleGeocodingClient;
        this.googleMapsMapper = googleMapsMapper;
    }

    public AutocompleteResponseDTO autocomplete(AutocompleteRequestDTO request) {
        GoogleAutocompleteResponseDTO raw = googlePlacesClient.autocomplete(request);
        var limited = googleMapsMapper.toAutocomplete(raw).suggestions().stream()
                .limit(3)
                .toList();

        return new AutocompleteResponseDTO(limited);
    }

    public PlaceDetailsResponseDTO placeDetails(String placeId, String sessionToken) {
        GooglePlaceDetailsResponseDTO raw = googlePlacesClient.placeDetails(placeId, sessionToken);
        return googleMapsMapper.toPlaceDetails(raw);
    }

    public ReverseResponseDTO reverse(double latitude, double longitude) {
        GoogleReverseResponseDTO raw = googleGeocodingClient.reverse(latitude, longitude);
        return googleMapsMapper.toReverse(raw);
    }

}
