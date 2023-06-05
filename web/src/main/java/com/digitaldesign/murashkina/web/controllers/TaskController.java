package com.digitaldesign.murashkina.web.controllers;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.dto.request.task.SearchTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskStatusRequest;
import com.digitaldesign.murashkina.dto.response.TaskResponse;
import com.digitaldesign.murashkina.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@Tag(name = "TaskController", description = "Контроллер задачи")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Создание задачи")
    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest request, Principal principal) {
        TaskResponse taskResponse = taskService.create(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @Operation(summary = "Изменене задачи")
    @PutMapping(path = "/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("taskId") String taskId, @RequestBody @Valid UpdateTaskRequest request, Principal principal) {
        TaskResponse taskResponse = taskService.update(request, UUID.fromString(taskId), principal.getName());
        return ResponseEntity.ok(taskResponse);
    }

    @Operation(summary = "Изменение статуса задачи")
    @PutMapping(path = "/status/{taskId}")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable("taskId") String taskId, @RequestBody UpdateTaskStatusRequest request, Principal principal) {
        System.out.println("WWWWWWw");
        TaskResponse taskResponse = taskService.updateStatus(request, UUID.fromString(taskId), principal.getName());
        return ResponseEntity.ok(taskResponse);
    }

    @Operation(summary = "Получить информацию о задаче")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable("taskId") String taskId) {
        TaskResponse task = taskService.findById(UUID.fromString(taskId));
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Поиск задачи")
    @GetMapping(value = "/search", params = {"taskName", "executor", "author", "deadline", "createdAt", "status"})
    public List<TaskResponse> searchTask(@RequestParam(value = "taskName", required = false) String taskName,
                                         @RequestParam(value = "executor", required = false) UUID executor,
                                         @RequestParam(value = "author", required = false) UUID author,
                                         @RequestParam(value = "deadline", required = false) Date deadline,
                                         @RequestParam(value = "createdAt", required = false) Date createdAt,
                                         @RequestParam(value = "status", required = false) TaskStatus status) {
        SearchTaskRequest request = SearchTaskRequest.builder().taskName(taskName)
                .executor(executor)
                .author(author)
                .deadline(deadline)
                .createdAt(createdAt)
                .status(status).build();
        return taskService.search(request);
    }
}