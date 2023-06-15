package com.digitaldesign.murashkina.dto.request.project;


import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProjectStatus {
    private ProjStatus status;
}
