package com.digitaldesign.murashkina.dto.request.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchProjRequest {
    private String projectId;
    private String projectName;
}
