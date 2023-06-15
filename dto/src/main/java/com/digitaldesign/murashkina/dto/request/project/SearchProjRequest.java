package com.digitaldesign.murashkina.dto.request.project;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SearchProjRequest {
    private UUID id;
    private List<ProjStatus> statuses;
    private String projectName;
}
