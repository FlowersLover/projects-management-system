package com.digitaldesign.murashkina.models.project;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Builder
@Setter
@Getter
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String projectName;
    private String description;
    private ProjStatus projectStatus;

}
