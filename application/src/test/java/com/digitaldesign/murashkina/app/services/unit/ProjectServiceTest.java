package com.digitaldesign.murashkina.app.services.unit;


import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.request.project.UpdateProjectStatus;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.services.ProjectService;
import com.digitaldesign.murashkina.services.exceptions.project.InvalidProjectStatusException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectIsNullException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.project.StatusUnaviablException;
import com.digitaldesign.murashkina.services.mapping.ProjectMapper;
import com.digitaldesign.murashkina.services.specifications.ProjectSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.digitaldesign.murashkina.app.services.unit.TestEntitiesCreator.createTestProject;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {
    @Spy
    ProjectRepository projectRepository;
    @Mock
    ProjectMapper projectMapper;
    @Spy
    ProjectSpecification ps;
    @InjectMocks
    ProjectService projectService;

    @Test
    public void createProject() {
        ProjectRequest request = ProjectRequest.builder()
                .projectName("testproject")
                .description("description test").build();
        Project project = Project.builder()
                .projectId(UUID.randomUUID())
                .projectStatus(ProjStatus.DRAFT)
                .projectName("testproject")
                .description("description test").build();
        ProjectResponse response = ProjectResponse.builder()
                .projectId(project.getProjectId().toString())
                .projectStatus(ProjStatus.DRAFT)
                .projectName("testproject")
                .description("description test").build();
        when(projectMapper.toEntity(request)).thenReturn(project);
        when(projectRepository.save(projectMapper.toEntity(request))).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(response);
        ProjectResponse actuallProject = projectService.create(request);
        Assertions.assertNotNull(actuallProject);
        Assertions.assertEquals(project.getProjectId().toString(), actuallProject.getProjectId());
        Assertions.assertNotNull(actuallProject.getProjectName());
        Assertions.assertNotNull(actuallProject.getProjectStatus());
        Assertions.assertNotNull(actuallProject.getDescription());
    }

    @Test
    public void createProjectIsNull() {
        Assertions.assertThrows(ProjectIsNullException.class, () -> projectService.create(ProjectRequest.builder().build()));
    }

    @Test
    public void updateProject() {
        ProjectRequest request = ProjectRequest.builder()
                .projectName("testproject")
                .description("description test").build();
        Project project = createTestProject();
        ProjectResponse response = ProjectResponse.builder()
                .projectId(project.getProjectId().toString())
                .projectStatus(ProjStatus.DRAFT)
                .projectName("testproject")
                .description("description test").build();
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectRepository.existsById(any())).thenReturn(true);
        when(projectMapper.toEntity(request)).thenReturn(project);
        when(projectRepository.save(projectMapper.toEntity(request))).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(response);
        ProjectResponse actuallProject = projectService.update(request, project.getProjectId());
        Assertions.assertNotNull(actuallProject);
        verify(projectRepository, times(1)).save(any(Project.class));
        Assertions.assertEquals(project.getProjectId().toString(), actuallProject.getProjectId());
        Assertions.assertNotNull(actuallProject.getProjectName());
        Assertions.assertNotNull(actuallProject.getProjectStatus());
        Assertions.assertNotNull(actuallProject.getDescription());
    }


    @Test
    void updateProject_projectNotFound() {
        ProjectRequest request = ProjectRequest.builder()
                .projectName("updatetestproject2")
                .description("updatedescription test").build();
        Assertions.assertThrows(ProjectNotFoundException.class,
                () -> projectService.update(request, UUID.randomUUID()));
    }

    @Test
    public void updateStatus() {
        UpdateProjectStatus request = new UpdateProjectStatus(ProjStatus.DEVELOP.name());
        Project project = createTestProject();
        project.setProjectStatus(ProjStatus.DEVELOP);
        ProjectResponse response = ProjectResponse.builder()
                .projectId(project.getProjectId().toString())
                .projectStatus(ProjStatus.DEVELOP)
                .projectName("testproject")
                .description("description test").build();
        when(projectRepository.existsById(any())).thenReturn(true);
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(response);
        ProjectResponse actuallProject = projectService.updateStatus(request.getStatus(), project.getProjectId());
        Assertions.assertNotNull(actuallProject);
        verify(projectRepository, times(1)).setProjectStatusById(any(ProjStatus.class), any(UUID.class));
        Assertions.assertEquals(project.getProjectId().toString(), actuallProject.getProjectId());
        Assertions.assertNotNull(actuallProject.getProjectName());
        Assertions.assertNotNull(actuallProject.getProjectStatus());
        Assertions.assertNotNull(actuallProject.getDescription());
    }

    @Test
    void updateProjectStatus_ProjectNotFound() {
        ProjStatus status = ProjStatus.COMPLETED;
        Assertions.assertThrows(ProjectNotFoundException.class,
                () -> projectService.updateStatus(status.name(), UUID.randomUUID()));
    }

    private ProjectResponse createTestProjectResponse(String projectId) {
        ProjectResponse response = ProjectResponse.builder()
                .projectId(projectId)
                .projectStatus(ProjStatus.DEVELOP)
                .projectName("testproject")
                .description("description test").build();
        return response;
    }

    @Test
    void updateProjectStatus_statusNotAviable() {
        Project project = getTestProj();
        project.setProjectStatus(ProjStatus.COMPLETED);
        ProjectResponse response = createTestProjectResponse(project.getProjectId().toString());
        when(projectRepository.existsById(any())).thenReturn(true);
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        Assertions.assertThrows(StatusUnaviablException.class,
                () -> projectService.updateStatus(ProjStatus.DRAFT.name(), project.getProjectId()));
    }

    @Test
    void updateProjectStatus_invalidStatus() {
        Project project = getTestProj();
        when(projectRepository.existsById(any())).thenReturn(true);
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        Assertions.assertThrows(InvalidProjectStatusException.class,
                () -> projectService.updateStatus("error_status", project.getProjectId()));
    }

    @Test
    public void searchEmployee() {
        SearchProjRequest request = SearchProjRequest.builder()
                .projectName("testproject").build();
        Project project = Project.builder()
                .projectId(UUID.randomUUID())
                .projectStatus(ProjStatus.DEVELOP)
                .projectName("testproject")
                .description("description test").build();
        Project project2 = Project.builder()
                .projectId(UUID.randomUUID())
                .projectStatus(ProjStatus.TEST)
                .projectName("testproject")
                .description("description test").build();
        ProjectResponse response = ProjectResponse.builder()
                .projectId(project.getProjectId().toString())
                .projectStatus(ProjStatus.DEVELOP)
                .projectName("testproject")
                .description("description test").build();
        ProjectResponse response2 = ProjectResponse.builder()
                .projectId(project2.getProjectId().toString())
                .projectStatus(ProjStatus.TEST)
                .projectName("testproject")
                .description("description test").build();
        when(projectRepository.findAll(any(Specification.class))).thenReturn(List.of(project, project2));
        when(projectMapper.toDto(project)).thenReturn(response);
        when(projectMapper.toDto(project2)).thenReturn(response2);
        List<ProjectResponse> searchProjects = projectService.search(request);

        Assertions.assertNotNull(searchProjects);
        verify(projectRepository, times(1)).findAll(any(Specification.class));
        Assertions.assertEquals(project.getProjectId().toString(), searchProjects.get(0).getProjectId());
        Assertions.assertNotNull(searchProjects.get(0).getProjectName());
        Assertions.assertNotNull(searchProjects.get(0).getProjectStatus());
        Assertions.assertNotNull(searchProjects.get(0).getDescription());

        Assertions.assertEquals(project2.getProjectId().toString(), searchProjects.get(1).getProjectId());
        Assertions.assertNotNull(searchProjects.get(1).getProjectName());
        Assertions.assertNotNull(searchProjects.get(1).getProjectStatus());
        Assertions.assertNotNull(searchProjects.get(1).getDescription());
    }

    @Test
    public void findById() {
        Project project = createTestProject();
        ProjectResponse response = ProjectResponse.builder()
                .projectId(project.getProjectId().toString())
                .projectStatus(ProjStatus.DEVELOP)
                .projectName("testproject")
                .description("description test").build();
        when(projectRepository.existsById(any())).thenReturn(true);
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(response);
        ProjectResponse actuallProject = projectService.getProject(project.getProjectId());
        Assertions.assertNotNull(actuallProject);
        verify(projectRepository, times(1)).findById(any(UUID.class));
        Assertions.assertEquals(project.getProjectId().toString(), actuallProject.getProjectId());
        Assertions.assertNotNull(actuallProject.getProjectName());
        Assertions.assertNotNull(actuallProject.getProjectStatus());
        Assertions.assertNotNull(actuallProject.getDescription());
    }

    private Project getTestProj() {
        Project project = Project.builder()
                .projectId(UUID.randomUUID())
                .projectStatus(ProjStatus.DRAFT)
                .projectName("testproject")
                .description("description test").build();
        return project;
    }
}
