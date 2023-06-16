package com.digitaldesign.murashkina.web.controllers;

import com.digitaldesign.murashkina.dto.request.team.DeleteMember;
import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@Tag(name = "TeamController", description = "Контроллер команды")
@Log4j2
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "Создание участника проекта")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping()
    public ResponseEntity<TeamDto> createMember(@RequestBody @Valid TeamDto request) {
        TeamDto member = teamService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @Operation(summary = "Удаление участника проекта")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping()
    public ResponseEntity<TeamDto> deleteMember(@RequestBody DeleteMember request) {
        TeamDto teamDto = teamService.deleteMember(request);
        return ResponseEntity.ok().body(teamDto);
    }

}
