package com.digitaldesign.murashkina.services.mapping;

import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
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

    public TaskResponse toDto(Task model) {
        TaskResponse dto = this.modelMapper.map(model, TaskResponse.class);
        return dto;
    }
}
