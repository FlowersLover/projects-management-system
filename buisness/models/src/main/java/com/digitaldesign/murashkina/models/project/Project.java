package com.digitaldesign.murashkina.models.project;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;
@Getter
@Setter
@Builder
@Entity
@Table(name = "project")
@AllArgsConstructor
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "project_name")
    @NonNull
    private String projectName;

    @Column(name = "description")
    private String description;

    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private ProjStatus projectStatus;

    public Project() {

    }
}
