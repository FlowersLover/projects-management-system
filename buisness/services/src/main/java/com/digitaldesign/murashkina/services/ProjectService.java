package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.services.mapping.ProjectMapper;
import com.digitaldesign.murashkina.services.specifications.ProjectSpecification;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectSpecification ps;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectSpecification ps, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.ps = ps;
        this.projectMapper = projectMapper;
    }

    public ProjectResponse create(ProjectRequest createRequest) {
        if (createRequest == null
                || createRequest.getProjectName() == null) {
            throw new IllegalArgumentException("Project is null");
        }
        Project project = projectMapper.toEntity(createRequest);
        project.setProjectStatus(ProjStatus.DRAFT);

        /*Project newProject = Project.builder()
                .projectName(createRequest.getProjectName())
                .description(createRequest.getDescription())
                .projectStatus(ProjStatus.DRAFT)
                .build();*/
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    public ProjectResponse update(ProjectRequest request, UUID id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new IllegalArgumentException("Project not found");
        }
        Project newpProject = projectMapper.toEntity(request);
        newpProject.setId(project.get().getId());
        newpProject.setProjectStatus(project.get().getProjectStatus());
        projectRepository.save(newpProject);
        return projectMapper.toDto(newpProject);
    }

    @Transactional
    public ProjectResponse updateStatus(ProjStatus status, UUID id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new IllegalArgumentException("Project not found");
        }
        if (status.getStatusNumber() < projectRepository.findById(id).get().getProjectStatus().getStatusNumber()) {
            return null;
        }
        projectRepository.setProjectStatusById(status, id);
        return projectMapper.toDto(projectRepository.findById(id).get());
    }

    public List<ProjectResponse> search(SearchProjRequest searchFilter) {
        List<Project> projects = new ArrayList<>();
        projects.addAll(projectRepository.findAll(ps.projectNameLike(searchFilter.getProjectName())
                .or(ps.idEquals(searchFilter.getId()))
                .or(ps.statusEquals(searchFilter.getStatuses()))));
        return projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
    }

}
