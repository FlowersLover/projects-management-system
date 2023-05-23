package com.digitaldesign.murashkina.dto.request.project;


import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UpdateProjectStatus {
    private String projectId;
    private ProjStatus status;
}
