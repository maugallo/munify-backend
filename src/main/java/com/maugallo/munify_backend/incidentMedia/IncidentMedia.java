package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.incident.Incident;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column
    private String type;

    @Column
    private String url;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

}
