package com.digitaldesign.murashkina.web.controllers;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.request.project.UpdateProjectStatus;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@Tag(name = "ProjectController", description = "Контроллер проекта")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Создание проекта")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping()
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        ProjectResponse projectResponse = projectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }

    @Operation(summary = "Изменение проекта")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(path = "/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("projectId") String projectId, @RequestBody @Valid ProjectRequest request) {
        ProjectResponse update = projectService.update(request, UUID.fromString(projectId));
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @Operation(summary = "Изменение статуса проекта")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(path = "/status/{projectId}")
    public ResponseEntity<ProjectResponse> updateStatus(@PathVariable("projectId") String projectId,
                                                        @RequestBody @Valid UpdateProjectStatus request) {
        ProjectResponse updated = projectService.updateStatus(request.getStatus(), UUID.fromString(projectId));
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Получить информацию о проекте")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("projectId") String projectId) {
        ProjectResponse projectResponse = projectService.getProject(UUID.fromString(projectId));
        return ResponseEntity.ok(projectResponse);
    }

    @Operation(summary = "Поиск проекта")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/search")
    public ResponseEntity<List<ProjectResponse>> searchProject(@RequestParam(value = "id", required = false) UUID id,
                                                               @RequestParam(value = "statuses", required = false) List<ProjStatus> statuses,
                                                               @RequestParam(value = "projectName", required = false) String projectName) {
        SearchProjRequest request = SearchProjRequest.builder()
                .id(id)
                .statuses(statuses)
                .projectName(projectName)
                .build();
        List<ProjectResponse> search = projectService.search(request);
        return ResponseEntity.ok(search);
    }
}

