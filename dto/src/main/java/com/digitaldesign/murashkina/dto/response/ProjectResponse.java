package com.digitaldesign.murashkina.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectResponse {
    private String projectId;
    private String projectName;
    private String description;
}
