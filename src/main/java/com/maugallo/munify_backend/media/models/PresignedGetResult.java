package com.maugallo.munify_backend.media.models;

public record PresignedGetResult(
        String url,
        Long expiresAtSec
) { }
