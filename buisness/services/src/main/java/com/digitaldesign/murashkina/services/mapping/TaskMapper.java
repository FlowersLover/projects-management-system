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
        return this.modelMapper.map(request, Task.class);
    }

    public Task toEntity(UpdateTaskRequest request) {
        return this.modelMapper.map(request, Task.class);
    }

    public TaskRequest toDto(UpdateTaskRequest request) {
        return this.modelMapper.map(request, TaskRequest.class);
    }

    public TaskResponse toDto(Task model) {
        return TaskResponse.builder()
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
    }
}
