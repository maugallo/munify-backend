package com.maugallo.munify_backend.media;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "storage")
public record StorageProps(
        String  bucket,
        String  region,
        String  endpoint,    // http://localhost:9000 (MinIO)
        String  accessKey,
        String  secretKey,
        Duration putTtl,
        Duration getTtl
) { }
