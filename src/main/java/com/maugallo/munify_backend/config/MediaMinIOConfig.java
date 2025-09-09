package com.maugallo.munify_backend.config;

import com.maugallo.munify_backend.media.StorageProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Profile("dev")
@Configuration
@EnableConfigurationProperties(StorageProps.class)
public class MediaMinIOConfig {

    @Bean
    S3Client s3Client(StorageProps storageProps) {
        return S3Client.builder()
                .endpointOverride(URI.create(storageProps.endpoint()))
                .region(Region.of(storageProps.region()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(storageProps.accessKey(), storageProps.secretKey())))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

    @Bean
    S3Presigner s3Presigner(StorageProps storageProps) {
        return S3Presigner.builder()
                .endpointOverride(URI.create(storageProps.endpoint()))
                .region(Region.of(storageProps.region()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(storageProps.accessKey(), storageProps.secretKey())))
                .build();
    }

}
