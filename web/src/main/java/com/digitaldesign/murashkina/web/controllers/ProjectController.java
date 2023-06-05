package com.digitaldesign.murashkina.web.controllers;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.request.project.UpdateProjectStatus;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @PostMapping()
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        ProjectResponse projectResponse = projectService.create(request);
        return ResponseEntity.ok(projectResponse);
    }

    @Operation(summary = "Изменение проекта")
    @PutMapping(path = "/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("projectId") String projectId, @RequestBody @Valid ProjectRequest request) {
        ProjectResponse update = projectService.update(request, UUID.fromString(projectId));
        return ResponseEntity.ok(update);
    }

    @Operation(summary = "Изменение статуса проекта")
    @PutMapping(path = "/status/{projectId}")
    public ResponseEntity<ProjectResponse> updateStatus(@PathVariable("projectId") String projectId,
                                                        @RequestBody @Valid UpdateProjectStatus request) {
        ProjectResponse updated = projectService.updateStatus(request.getStatus(), UUID.fromString(projectId));
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Получить информацию о проекте")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("projectId") String projectId) {
        ProjectResponse projectResponse = projectService.findById(UUID.fromString(projectId));
        return ResponseEntity.ok(projectResponse);
    }

    @Operation(summary = "Поиск проекта")
    @GetMapping(value = "/search", params = {"id", "statuses", "projectName"})
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
