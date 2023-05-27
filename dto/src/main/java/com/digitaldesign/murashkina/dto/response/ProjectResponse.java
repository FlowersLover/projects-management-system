package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectResponse {
    private String projectId;
    private String projectName;
    private String description;
    private ProjStatus projectStatus;
}
