package com.maugallo.munify_backend.media;

import com.maugallo.munify_backend.incidentMedia.dto.ReadUrlDTO;
import org.springframework.stereotype.Component;

@Component
public class MediaLinkBuilderImpl implements MediaLinkBuilder {

    private final MediaStorage mediaStorage;
    private final StorageProps props;

    public MediaLinkBuilderImpl(MediaStorage mediaStorage, StorageProps props) {
        this.mediaStorage = mediaStorage;
        this.props = props;
    }

    @Override
    public ReadUrlDTO buildTemporalUrl(String storageKey) {
        var presigned = mediaStorage.presignGet(storageKey);
        return new ReadUrlDTO(presigned.url(), presigned.expiresAtSec());
    }

    @Override
    public ReadUrlDTO buildPermanentUrl(String storageKey) {
        var readUrl = "";
        if (props.endpoint() != null && !props.endpoint().isBlank()) {
            // MinIO (path-style): http://host:9000/{bucket}/{key}
            readUrl = props.endpoint().replaceAll("/$", "")
                    + "/" + props.bucket()
                    + "/" + encodePath(storageKey);
        } else {
            // AWS S3 (virtual-host): https://{bucket}.s3.{region}.amazonaws.com/{key}
            readUrl = "https://%s.s3.%s.amazonaws.com/%s"
                    .formatted(props.bucket(), props.region(), encodePath(storageKey));
        }

        return new ReadUrlDTO(readUrl, null);
    }

    private static String encodePath(String key) {
        return java.util.Arrays.stream(key.split("/"))
                .map(seg -> java.net.URLEncoder.encode(seg, java.nio.charset.StandardCharsets.UTF_8)
                        .replace("+", "%20"))
                .collect(java.util.stream.Collectors.joining("/"));
    }

}
