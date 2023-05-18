package com.digitaldesign.murashkina.models.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
public class Project {
    private UUID id;
    private String projectName;
    private String description;
    private ProjStatus projectStatus;

}
