package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.incident.Incident;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentMedia {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private IncidentMediaType type;

    @Column
    private String storageKey;

    @Column
    private String mime;

    @Column
    private Long size;

    @Column
    private String etag;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

}
