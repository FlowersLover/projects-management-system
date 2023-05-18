package com.digitaldesign.murashkina.dto.request.project;


import com.digitaldesign.murashkina.models.project.ProjStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProjectStatus {
    private String projectId;
    private ProjStatus status;
}
