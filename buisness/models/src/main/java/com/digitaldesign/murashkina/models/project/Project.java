package com.digitaldesign.murashkina.models.project;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Builder
public class Project {
    private UUID id;
    private String projectName;
    private String description;
    private ProjStatus projectStatus;

}
