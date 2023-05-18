package com.digitaldesign.murashkina.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchProjRequest {
    private String projectId;
    private String projectName;
}
