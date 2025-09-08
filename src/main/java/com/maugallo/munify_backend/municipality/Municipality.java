package com.maugallo.munify_backend.municipality;

import com.maugallo.munify_backend.municipalityFeature.MunicipalityFeature;
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
public class Municipality {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String logoUrl;

    @Column
    private String primaryColor;

    @Column
    private String secondaryColor;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime  updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MunicipalityFeature> features = new ArrayList<>();

}
