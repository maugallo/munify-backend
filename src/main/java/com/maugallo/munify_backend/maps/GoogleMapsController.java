package com.maugallo.munify_backend.maps;

import com.maugallo.munify_backend.maps.dto.autocomplete.AutocompleteRequestDTO;
import com.maugallo.munify_backend.maps.dto.autocomplete.AutocompleteResponseDTO;
import com.maugallo.munify_backend.maps.dto.geocoding.ReverseResponseDTO;
import com.maugallo.munify_backend.maps.dto.placeDetails.PlaceDetailsResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GoogleMapsController {

    private final GoogleMapsService googleMapsService;

    public GoogleMapsController(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @PostMapping("/places/autocomplete")
    public ResponseEntity<AutocompleteResponseDTO> autocomplete(@RequestBody AutocompleteRequestDTO body) {
        return ResponseEntity.ok(googleMapsService.autocomplete(body));
    }

    @GetMapping("/places/details")
    public ResponseEntity<PlaceDetailsResponseDTO> details(
            @RequestParam String placeId,
            @RequestParam String sessionToken
    ) {
        return ResponseEntity.ok(googleMapsService.placeDetails(placeId, sessionToken));
    }

    @GetMapping("/geocode/reverse")
    public ResponseEntity<ReverseResponseDTO> reverse(
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        return ResponseEntity.ok(googleMapsService.reverse(lat, lng));
    }

}
