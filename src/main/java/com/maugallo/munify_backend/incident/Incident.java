package com.maugallo.munify_backend.incident;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maugallo.munify_backend.citizen.Citizen;
import com.maugallo.munify_backend.employee.Employee;
import com.maugallo.munify_backend.incidentMedia.IncidentMedia;
import com.maugallo.munify_backend.municipality.Municipality;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    @Enumerated(EnumType.STRING)
    private IncidentCategory category;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private Boolean isEnabled;

    @Column(name = "municipality_id", nullable = false)
    private Long municipalityId;

    @Column(name = "citizen_id", nullable = false)
    private Long citizenId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Builder.Default
    @OneToMany(mappedBy = "incident")
    private List<IncidentMedia> medias = new ArrayList<>();

    /* Asociaciones "ocultas" - Temporal de MVP
    * Solo para que JPA genere las FK */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    protected Municipality municipality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    protected Citizen citizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    protected Employee employee;

}
