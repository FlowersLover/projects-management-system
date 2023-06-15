package com.digitaldesign.murashkina.services.mapping;

import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.models.project.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {
    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Project toEntity(ProjectRequest request) {
        Project project = this.modelMapper.map(request, Project.class);
        return project;
    }

    public ProjectResponse toDto(Project model) {
        ProjectResponse dto = this.modelMapper.map(model, ProjectResponse.class);
        return dto;
    }
}
