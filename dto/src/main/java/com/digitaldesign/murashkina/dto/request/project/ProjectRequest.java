package com.digitaldesign.murashkina.dto.request.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectRequest {
    private String projectName;
    private String description;
}
