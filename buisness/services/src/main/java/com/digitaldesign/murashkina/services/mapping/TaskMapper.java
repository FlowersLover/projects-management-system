package com.digitaldesign.murashkina.services.mapping;

import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskRequest;
import com.digitaldesign.murashkina.dto.response.TaskResponse;
import com.digitaldesign.murashkina.models.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task toEntity(TaskRequest request) {
        Task task = this.modelMapper.map(request, Task.class);
        return task;
    }
    public Task toEntity(UpdateTaskRequest request) {
        Task task = this.modelMapper.map(request, Task.class);
        return task;
    }

    public TaskRequest toDto(UpdateTaskRequest request) {
        TaskRequest task = this.modelMapper.map(request, TaskRequest.class);
        return task;
    }

    public TaskResponse toDto(Task model) {
        TaskResponse dto = TaskResponse.builder()
                .executor(model.getExecutor().getId())
                .author(model.getAuthor().getId())
                .id(model.getId())
                .deadline(model.getDeadline())
                .description(model.getDescription())
                .hoursToCompleteTask(model.getHoursToCompleteTask())
                .lastChanged(model.getLastChanged())
                .taskName(model.getTaskName())
                .status(model.getStatus())
                .createdAt(model.getCreatedAt()).build();
        return dto;
    }
}
