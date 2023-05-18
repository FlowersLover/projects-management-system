package com.digitaldesign.murashkina.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectRequest {
    private String projectName;
    private String description;
}
