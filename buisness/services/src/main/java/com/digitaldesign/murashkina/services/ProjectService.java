package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.services.exceptions.project.InvalidProjectStatusException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectIsNullException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.project.StatusUnaviablException;
import com.digitaldesign.murashkina.services.mapping.ProjectMapper;
import com.digitaldesign.murashkina.services.specifications.ProjectSpecification;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.EnumUtils;
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
        projectIsNull(createRequest);
        Project project = projectMapper.toEntity(createRequest);
        project.setProjectStatus(ProjStatus.DRAFT);
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }


    public ProjectResponse update(ProjectRequest request, UUID id) {
        projectIsExist(id);
        Optional<Project> project = projectRepository.findById(id);
        Project newpProject = projectMapper.toEntity(request);
        newpProject.setId(project.get().getId());
        newpProject.setProjectStatus(project.get().getProjectStatus());
        projectRepository.save(newpProject);
        return projectMapper.toDto(newpProject);
    }

    private void invalidProjectStatusException(String status) {
        if (!EnumUtils.isValidEnum(ProjStatus.class, status)) {
            throw new InvalidProjectStatusException();
        }
    }

    @Transactional
    public ProjectResponse updateStatus(String status, UUID id) {
        projectIsExist(id);
        statusIsAviable(ProjStatus.valueOf(status), id);
        invalidProjectStatusException(status);
        projectRepository.setProjectStatusById(ProjStatus.valueOf(status), id);
        Project project = projectRepository.findById(id).get();
        return projectMapper.toDto(project);
    }

    public List<ProjectResponse> search(SearchProjRequest searchFilter) {
        List<Project> projects = new ArrayList<>();
        projects.addAll(projectRepository.findAll(ps.getSpecification(searchFilter)));
        return projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
    }

    public ProjectResponse findById(UUID uuid) {
        projectIsExist(uuid);
        Optional<Project> project = projectRepository.findById(uuid);
        return projectMapper.toDto(project.get());
    }

    public boolean existById(String projectId) {
        return projectRepository.existsById(UUID.fromString(projectId));
    }

    private void projectIsNull(ProjectRequest createRequest) {
        if (createRequest == null
                || createRequest.getProjectName() == null) {
            throw new ProjectIsNullException();
        }
    }

    private void projectIsExist(UUID id) {
        if (!existById(id.toString())) {
            throw new ProjectNotFoundException();
        }
    }

    private void statusIsAviable(ProjStatus status, UUID id) {
        if (!statusIsAviable(id, status)) {
            throw new StatusUnaviablException();
        }
    }

    public boolean statusIsAviable(UUID id, ProjStatus status) {
        if (status.getStatusNumber() >= projectRepository.findById(id).get().getProjectStatus().getStatusNumber()) {
            return true;
        }
        return false;
    }
}
