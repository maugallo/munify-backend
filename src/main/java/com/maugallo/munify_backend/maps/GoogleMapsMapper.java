package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.maps.dto.autocomplete.AutocompleteResponseDTO;
import com.maugallo.munify_backend.maps.dto.autocomplete.GoogleAutocompleteResponseDTO;
import com.maugallo.munify_backend.maps.dto.autocomplete.SuggestionDTO;
import com.maugallo.munify_backend.maps.dto.geocoding.GoogleReverseResponseDTO;
import com.maugallo.munify_backend.maps.dto.geocoding.ReverseResponseDTO;
import com.maugallo.munify_backend.maps.dto.placeDetails.GooglePlaceDetailsResponseDTO;
import com.maugallo.munify_backend.maps.dto.placeDetails.PlaceDetailsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface GoogleMapsMapper {

    /* Autocomplete  */
    @Mapping(target = "suggestions", source = "suggestions")
    AutocompleteResponseDTO toAutocomplete(GoogleAutocompleteResponseDTO autocomplete);

    @Mapping(target = "placeId", source = "placePrediction.placeId")
    @Mapping(target = "primary", source = "placePrediction", qualifiedByName = "extractPrimary")
    @Mapping(target = "secondary", source = "placePrediction", qualifiedByName = "extractSecondary")
    SuggestionDTO toSuggestion(GoogleAutocompleteResponseDTO.Suggestion suggestion);

    @Named("extractPrimary")
    default String extractPrimary(GoogleAutocompleteResponseDTO.PlacePrediction placePrediction) {
        if (placePrediction == null) return null;

        var structuredFormat = placePrediction.structuredFormat();
        if (structuredFormat != null && structuredFormat.mainText() != null && structuredFormat.mainText().text() != null) {
            return structuredFormat.mainText().text();
        }

        return placePrediction.text() != null ? placePrediction.text().text() : null;
    }

    @Named("extractSecondary")
    default String extractSecondary(GoogleAutocompleteResponseDTO.PlacePrediction placePrediction) {
        if (placePrediction == null) return null;

        var structuredFormat = placePrediction.structuredFormat();
        return (structuredFormat != null && structuredFormat.secondaryText() != null) ? structuredFormat.secondaryText().text() : null;
    }

    /* Place Details  */
    @Mapping(target = "location", source = "location")
    @Mapping(target = "address.formatted", source = "formattedAddress")
    @Mapping(target = "viewport", source = "viewport")
    PlaceDetailsResponseDTO toPlaceDetails(GooglePlaceDetailsResponseDTO placeDetails);

    @Mapping(target = "latitude",  source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    PlaceDetailsResponseDTO.LatLng toLatLng(GooglePlaceDetailsResponseDTO.LatLng latLng);

    @Mapping(target = "northeast", source = "high")
    @Mapping(target = "southwest", source = "low")
    PlaceDetailsResponseDTO.Viewport toViewport(GooglePlaceDetailsResponseDTO.Viewport viewport);

    /* Reverse Geocoding  */
    @Mapping(target = "address", source = "reverse", qualifiedByName = "firstOrEmpty")
    ReverseResponseDTO toReverse(GoogleReverseResponseDTO reverse);

    @Named("firstOrEmpty")
    default ReverseResponseDTO.Address firstOrEmpty(GoogleReverseResponseDTO reverse) {
        var list = reverse.results();
        if (list == null || list.isEmpty())
            return new ReverseResponseDTO.Address("");

        return new ReverseResponseDTO.Address(list.getFirst().formatted_address());
    }

}
