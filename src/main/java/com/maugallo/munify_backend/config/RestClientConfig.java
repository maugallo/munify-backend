package com.maugallo.munify_backend.config;

import com.maugallo.munify_backend.maps.GoogleMapsProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient placesRestClient(GoogleMapsProps props) {
        return RestClient.builder()
                .baseUrl(props.places().baseUrl())
                .defaultHeader("X-Goog-Api-Key", props.apiKey())
                .build();
    }

    @Bean
    RestClient geocodingRestClient(GoogleMapsProps props) {
        return RestClient.builder()
                .baseUrl(props.geocoding().baseUrl())
                .build();
    }

}
