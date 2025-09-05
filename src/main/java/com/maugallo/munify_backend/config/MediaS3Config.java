package com.maugallo.munify_backend.config;

import com.maugallo.munify_backend.media.StorageProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@Profile("prod")
@EnableConfigurationProperties(StorageProps.class)
public class MediaS3Config {

    @Bean
    S3Client s3Client(StorageProps props) {
        return S3Client.builder()
                .region(Region.of(props.region()))
                .build();
    }

    @Bean
    S3Presigner s3Presigner(StorageProps props) {
        return S3Presigner.builder()
                .region(Region.of(props.region()))
                .build();
    }

}
