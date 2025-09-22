package com.maugallo.munify_backend.maps;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.maps")
public record GoogleMapsProps(
   String apiKey,
   Service places,
   Service geocoding
) {
    public record Service(String baseUrl) { }
}
