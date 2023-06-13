package com.digitaldesign.murashkina.app.services.functional;

import com.digitaldesign.murashkina.app.services.integretion.BaseTest;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.services.ProjectService;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeIsNullException;
import com.digitaldesign.murashkina.services.exceptions.project.InvalidProjectStatusException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectIsNullException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.project.StatusUnaviablException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ProjectFuncTest extends BaseTest {
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;

    @AfterEach
    public void resetDb() {
        projectRepository.deleteAll();
    }

    @Test
    void createProjectTest() {
        ProjectRequest request = ProjectRequest.builder()
                .projectName("testproject")
                .description("description test").build();
        ProjectResponse projectResponse = projectService.create(request);
        Assertions.assertEquals(request.getProjectName(), projectResponse.getProjectName());
        Assertions.assertEquals(request.getDescription(), projectResponse.getDescription());
        Assertions.assertNotNull(projectResponse.getProjectId());
        Assertions.assertEquals(ProjStatus.DRAFT, projectResponse.getProjectStatus());
    }

    @Test
    void createProjectTest_projectIsNull() {
        Assertions.assertThrows(ProjectIsNullException.class, () -> projectService.create(ProjectRequest.builder().build()));
    }

    @Test
    void updateProjectTest() {
        Project project = getTestProj();
        ProjectRequest request = ProjectRequest.builder()
                .projectName("updatetestproject2")
                .description("updatedescription test").build();
        ProjectResponse projectResponse = projectService.update(request, project.getProjectId());
        Assertions.assertEquals(request.getProjectName(), projectResponse.getProjectName());
        Assertions.assertEquals(request.getDescription(), projectResponse.getDescription());
        Assertions.assertEquals(project.getProjectId().toString(), projectResponse.getProjectId());
        Assertions.assertEquals(ProjStatus.DRAFT, projectResponse.getProjectStatus());
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
    void updateProjectStatusTest() {
        ProjStatus status = ProjStatus.COMPLETED;
        Project project = getTestProj();
        ProjectResponse projectResponse = projectService.updateStatus(status.name(), project.getProjectId());
        Assertions.assertEquals(project.getProjectName(), projectResponse.getProjectName());
        Assertions.assertEquals(project.getDescription(), projectResponse.getDescription());
        Assertions.assertEquals(project.getProjectId().toString(), projectResponse.getProjectId());
        Assertions.assertEquals(status, projectResponse.getProjectStatus());
    }
    @Test
    void updateProjectStatus_ProjectNotFound() {
        ProjStatus status = ProjStatus.COMPLETED;
        Assertions.assertThrows(ProjectNotFoundException.class,
                () -> projectService.updateStatus(status.name(), UUID.randomUUID()));
    }
    @Test
    void updateProjectStatus_statusNotAviable() {
        Project project = getTestProj();
        projectService.updateStatus(ProjStatus.COMPLETED.name(), project.getProjectId());
        Assertions.assertThrows(StatusUnaviablException.class,
                () -> projectService.updateStatus(ProjStatus.DRAFT.name(), project.getProjectId()));
    }
    @Test
    void updateProjectStatus_invalidStatus() {
        Project project = getTestProj();
        Assertions.assertThrows(InvalidProjectStatusException.class,
                () -> projectService.updateStatus("error_status", project.getProjectId()));
    }

    @Test
    void searchProject_byStatusesAndProjecName() {
        List<Project> projectsList = getPrjectsList();
        SearchProjRequest searchProjRequest = SearchProjRequest.builder()
                .projectName(projectsList.get(1).getProjectName())
                .statuses(List.of(projectsList.get(1).getProjectStatus())).build();
        List<ProjectResponse> projectResponses = projectService.search(searchProjRequest);
        Assertions.assertEquals(projectsList.get(1).getProjectName(), projectResponses.get(0).getProjectName());
        Assertions.assertEquals(projectsList.get(1).getDescription(), projectResponses.get(0).getDescription());
        Assertions.assertEquals(projectsList.get(1).getProjectId().toString(), projectResponses.get(0).getProjectId());
        Assertions.assertEquals(projectsList.get(1).getProjectStatus(), projectResponses.get(0).getProjectStatus());
    }

    @Test
    @Repeat(5)
    void searchProject_byStatuses() {
        List<Project> projectsList = getPrjectsList();
        System.out.println(projectsList.get(0).getProjectId().toString());
        System.out.println(projectsList.get(1).getProjectId().toString());
        SearchProjRequest searchProjRequest = SearchProjRequest.builder()
                .statuses(List.of(ProjStatus.TEST)).build();
        List<ProjectResponse> projectResponses = projectService.search(searchProjRequest);
        Assertions.assertEquals(projectsList.get(0).getProjectName(), projectResponses.get(0).getProjectName());
        Assertions.assertEquals(projectsList.get(0).getDescription(), projectResponses.get(0).getDescription());
        Assertions.assertEquals(projectsList.get(0).getProjectId().toString(), projectResponses.get(0).getProjectId());
        Assertions.assertEquals(projectsList.get(0).getProjectStatus(), projectResponses.get(0).getProjectStatus());

        Assertions.assertEquals(projectsList.get(1).getProjectName(), projectResponses.get(1).getProjectName());
        Assertions.assertEquals(projectsList.get(1).getDescription(), projectResponses.get(1).getDescription());
        Assertions.assertEquals(projectsList.get(1).getProjectId().toString(), projectResponses.get(1).getProjectId());
        Assertions.assertEquals(projectsList.get(1).getProjectStatus(), projectResponses.get(1).getProjectStatus());
    }

    @Test
    void searchProject_byProjectId() {
        List<Project> projectsList = getPrjectsList();
        SearchProjRequest searchProjRequest = SearchProjRequest.builder()
                .id(projectsList.get(2).getProjectId()).build();
        List<ProjectResponse> projectResponses = projectService.search(searchProjRequest);
        Assertions.assertEquals(projectsList.get(2).getProjectName(), projectResponses.get(0).getProjectName());
        Assertions.assertEquals(projectsList.get(2).getDescription(), projectResponses.get(0).getDescription());
        Assertions.assertEquals(projectsList.get(2).getProjectId().toString(), projectResponses.get(0).getProjectId());
        Assertions.assertEquals(projectsList.get(2).getProjectStatus(), projectResponses.get(0).getProjectStatus());
    }
    @Test
    void getProject(){
        Project project = getTestProj();
        ProjectRequest request = ProjectRequest.builder()
                .projectName("testproject")
                .description("description test").build();
        ProjectResponse projectResponse = projectService.getProject(project.getProjectId());
        Assertions.assertEquals(request.getProjectName(), projectResponse.getProjectName());
        Assertions.assertEquals(request.getDescription(), projectResponse.getDescription());
        Assertions.assertEquals(project.getProjectId().toString(),projectResponse.getProjectId());
        Assertions.assertEquals(project.getProjectStatus(), projectResponse.getProjectStatus());
    }
    @Test
    void getProject_projectNotFound() {
        Assertions.assertThrows(ProjectNotFoundException.class,
                () -> projectService.getProject( UUID.randomUUID()));
    }
    private List<Project> getPrjectsList() {
        Project project = Project.builder()
                .projectStatus(ProjStatus.TEST)
                .projectName("firstproject")
                .description("description test").build();
        Project project1 = Project.builder()
                .projectStatus(ProjStatus.TEST)
                .projectName("projname")
                .description("description test").build();
        Project project2 = Project.builder()
                .projectStatus(ProjStatus.DRAFT)
                .projectName("projname")
                .description("description test").build();
        Project dbproject = projectRepository.save(project);
        Project dbproject1 = projectRepository.save(project1);
        Project dbproject2 = projectRepository.save(project2);
        return List.of(dbproject, dbproject1, dbproject2);
    }
    private Project getTestProj() {
        Project project = Project.builder()
                .projectStatus(ProjStatus.DRAFT)
                .projectName("testproject")
                .description("description test").build();
        return projectRepository.save(project);
    }
}
