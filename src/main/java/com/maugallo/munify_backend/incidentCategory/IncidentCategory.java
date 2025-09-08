package com.maugallo.munify_backend.incidentCategory;

import com.maugallo.munify_backend.incident.Incident;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Boolean isEnabled;

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Incident> incidents = new ArrayList<>();

}
