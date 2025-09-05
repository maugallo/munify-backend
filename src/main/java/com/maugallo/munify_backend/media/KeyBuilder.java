package com.maugallo.munify_backend.media;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class KeyBuilder {

    /** key = nombre del objeto dentro del bucket (similar a un path), usado por las APIs de S3/MinIO. */

    private final Clock clock;

    public KeyBuilder(Clock clock) { this.clock = clock; }

    public String forIncidentStaging(long municipalityId, String uuid, String ext) {
        var d = LocalDate.now(clock);
        return "municipalities/%d/staging/incidents/%04d/%02d/%02d/%s.%s"
                .formatted(municipalityId, d.getYear(), d.getMonthValue(), d.getDayOfMonth(), uuid, ext);
    }

    public String forIncidentPermanent(long municipalityId, long incidentId, String uuid, String ext) {
        return "municipalities/%d/incidents/%d/%s.%s".formatted(municipalityId, incidentId, uuid, ext);
    }

    public String forNewsStaging(long municipalityId, String uuid, String ext) {
        var d = LocalDate.now(clock);
        return "municipalities/%d/staging/news/%04d/%02d/%02d/%s.%s"
                .formatted(municipalityId, d.getYear(), d.getMonthValue(), d.getDayOfMonth(), uuid, ext);
    }

    public String forNewsPermanent(long municipalityId, long newsId, String uuid, String ext) {
        return "municipalities/%d/public/news/%d/%s.%s".formatted(municipalityId, newsId, uuid, ext);
    }

}
