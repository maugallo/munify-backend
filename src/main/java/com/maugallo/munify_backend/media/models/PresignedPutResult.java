package com.maugallo.munify_backend.media.models;

import java.util.Map;

public record PresignedPutResult(
    String url,
    Long expiresAtSec,
    Map<String, String > requiredHeaders
) { }
