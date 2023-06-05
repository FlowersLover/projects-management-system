package com.digitaldesign.murashkina.web.controllers;

import com.digitaldesign.murashkina.dto.request.team.DeleteMember;
import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.services.EmployeeService;
import com.digitaldesign.murashkina.services.ProjectService;
import com.digitaldesign.murashkina.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@Tag(name = "TeamController", description = "Контроллер команды")
public class TeamController {
    private final TeamService teamService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public TeamController(TeamService teamService, EmployeeService employeeService, ProjectService projectService) {
        this.teamService = teamService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @Operation(summary = "Создание участника проекта")
    @PostMapping()
    public ResponseEntity<TeamDto> createMember(@RequestBody @Valid TeamDto request) {
        TeamDto member = teamService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @Operation(summary = "Удаление участника проекта")
    @DeleteMapping()
    public ResponseEntity<TeamDto> deleteMember(@RequestBody DeleteMember request) {
        TeamDto teamDto = teamService.deleteMember(request);
        return ResponseEntity.ok().body(teamDto);
    }

}
