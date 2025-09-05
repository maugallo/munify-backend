package com.maugallo.munify_backend.media;

import com.maugallo.munify_backend.media.models.HeadObjectInfo;
import com.maugallo.munify_backend.media.models.PresignedGetResult;
import com.maugallo.munify_backend.media.models.PresignedPutResult;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Instant;
import java.util.Map;

@Component
public class MediaStorageImpl implements MediaStorage {

    /* Se utiliza el SDK S3 v2, compatible tanto con
    * S3 (prod) como con MinIO (dev). */

    private final S3Client s3;
    private final S3Presigner presigner;
    private final StorageProps props;

    public MediaStorageImpl(S3Client s3, S3Presigner presigner, StorageProps props) {
        this.s3 = s3;
        this.presigner = presigner;
        this.props = props;
    }

    @Override
    public PresignedPutResult presignPut(String storageKey, String contentType) {
        var putRequest = PutObjectRequest.builder()
                .bucket(props.bucket())
                .key(storageKey)
                .contentType(contentType)
                .build();

        var presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putRequest)
                .signatureDuration(props.putTtl())
                .build();

        var presigned = presigner.presignPutObject(presignRequest);
        Long expiry = Instant.now().plus(props.putTtl()).getEpochSecond();

        return new PresignedPutResult(
                presigned.url().toString(),
                expiry,
                Map.of("Content-Type", contentType)
        );
    }

    @Override
    public PresignedGetResult presignGet(String storageKey) {
        var getRequest = GetObjectRequest.builder()
                .bucket(props.bucket())
                .key(storageKey)
                .build();

        var presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getRequest)
                .signatureDuration(props.getTtl())
                .build();

        var presigned = presigner.presignGetObject(presignRequest);
        Long expiry = Instant.now().plus(props.getTtl()).getEpochSecond();

        return new PresignedGetResult(presigned.url().toString(), expiry);
    }

    @Override
    public HeadObjectInfo head(String storageKey) {
        var head = s3.headObject(HeadObjectRequest.builder()
                .bucket(props.bucket())
                .key(storageKey)
                .build());

        return new HeadObjectInfo(
                head.eTag(),
                head.contentLength(),
                head.contentType()
        );
    }

    @Override
    public void copyObject(String sourceKey, String destinationKey) {
        s3.copyObject(CopyObjectRequest.builder()
                .sourceBucket(props.bucket())
                .sourceKey(sourceKey)
                .destinationBucket(props.bucket())
                .destinationKey(destinationKey)
                .metadataDirective(MetadataDirective.COPY)
                .build());
    }

    @Override
    public void deleteObject(String storageKey) {
        s3.deleteObject(DeleteObjectRequest.builder()
                .bucket(props.bucket())
                .key(storageKey)
                .build());
    }

    @Override
    public String publicUrl(String storageKey) {
        if (props.endpoint() != null && !props.endpoint().isBlank()) {
            // MinIO (path-style): http://host:9000/{bucket}/{key}
            return props.endpoint().replaceAll("/$", "")
                    + "/" + props.bucket()
                    + "/" + urlEncode(storageKey);
        } else {
            // AWS S3 (virtual-host): https://{bucket}.s3.{region}.amazonaws.com/{key}
            return "https://%s.s3.%s.amazonaws.com/%s"
                    .formatted(props.bucket(), props.region(), urlEncode(storageKey));
        }
    }

    private static String urlEncode(String key) {
        return key.replace(" ", "%20");
    }

}
