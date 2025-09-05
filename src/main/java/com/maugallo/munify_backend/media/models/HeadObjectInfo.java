package com.maugallo.munify_backend.media.models;

public record HeadObjectInfo(
        String eTag,
        Long contentLength,
        String contentType
) { }
