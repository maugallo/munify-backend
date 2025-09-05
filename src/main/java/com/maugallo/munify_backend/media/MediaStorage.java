package com.maugallo.munify_backend.media;

import com.maugallo.munify_backend.media.models.HeadObjectInfo;
import com.maugallo.munify_backend.media.models.PresignedGetResult;
import com.maugallo.munify_backend.media.models.PresignedPutResult;

import java.time.Duration;

public interface MediaStorage {

    PresignedPutResult presignPut(String storageKey, String contentType);

    PresignedGetResult presignGet(String storageKey);

    HeadObjectInfo head(String storageKey);

    void copyObject(String sourceKey, String destinationKey);

    void deleteObject(String storageKey);

    String publicUrl(String storageKey);
}
