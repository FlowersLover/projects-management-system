package com.digitaldesign.murashkina.app.services.integretion.controllers;

import com.digitaldesign.murashkina.app.services.integretion.BaseTest;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.UpdateProjectStatus;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;

@AutoConfigureMockMvc
@SpringBootTest
public class ProjectControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ProjectRepository projectRepository;
    @AfterEach
    public void resetDb() {
        projectRepository.deleteAll();
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectRequest request = ProjectRequest.builder()
                .projectName("projectnew")
                .description("lalalla lala")
                .build();

        mockMvc.perform(
                        post("/project")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.projectId").isNotEmpty())
                .andExpect(jsonPath("$.projectName").value("projectnew"))
                .andExpect(jsonPath("$.description").value("lalalla lala"))
                .andExpect(jsonPath("$.projectStatus").value("DRAFT"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void update() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectRequest request = ProjectRequest.builder()
                .projectName("newprojectnew")
                .description("updatelala")
                .build();
        Project project = Project.builder()
                .projectName("project")
                .description("lalalla lala")
                .projectStatus(ProjStatus.DRAFT)
                .build();
        Project savedProject = projectRepository.save(project);
        String projectId = savedProject.getProjectId().toString();
        mockMvc.perform(
                        put("/project/{projectId}", projectId)
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").isNotEmpty())
                .andExpect(jsonPath("$.projectName").value(request.getProjectName()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.projectStatus").value("DRAFT"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void updateStatus() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateProjectStatus updateProjectStatus = new UpdateProjectStatus(ProjStatus.DEVELOP.name());
        Project project = Project.builder()
                .projectName("project")
                .description("lalalla lala")
                .projectStatus(ProjStatus.DRAFT)
                .build();
        Project savedProject = projectRepository.save(project);
        String projectId = savedProject.getProjectId().toString();
        mockMvc.perform(
                        put("/project/status/{projectId}", projectId)
                                .content(mapper.writeValueAsString(updateProjectStatus))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").isNotEmpty())
                .andExpect(jsonPath("$.projectName").value(project.getProjectName()))
                .andExpect(jsonPath("$.description").value(project.getDescription()))
                .andExpect(jsonPath("$.projectStatus").value(updateProjectStatus.getStatus()));
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void searchProjects() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Project project1 = Project.builder()
                .projectName("one")
                .description("oneonoenoe")
                .projectStatus(ProjStatus.DRAFT)
                .build();
        Project project2 = Project.builder()
                .projectName("two")
                .description("twotwotow")
                .projectStatus(ProjStatus.DRAFT)
                .build();
        Project project3 = Project.builder()
                .projectName("two")
                .description("twotwotow")
                .projectStatus(ProjStatus.COMPLETED)
                .build();
        Project savedproject1 = projectRepository.save(project1);
        Project savedproject2 = projectRepository.save(project2);
        Project savedproject3 = projectRepository.save(project3);

       mockMvc.perform(get("/project/search").param("statuses",savedproject1.getProjectStatus().name()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(savedproject1,savedproject2))));
        mockMvc.perform(get("/project/search")
                        .param("projectName",project2.getProjectName()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(savedproject2,savedproject3))));
    }
    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getProjectInfo() throws Exception {
        Project project = Project.builder()
                .projectName("project")
                .description("lalalla lala")
                .projectStatus(ProjStatus.DRAFT)
                .build();
        Project dbproject = projectRepository.save(project);
        mockMvc.perform(
                        get("/project/{id}", dbproject.getProjectId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(dbproject.getProjectId().toString()))
                .andExpect(jsonPath("$.projectName").value(project.getProjectName()))
                .andExpect(jsonPath("$.description").value(project.getDescription()))
                .andExpect(jsonPath("$.projectStatus").value(project.getProjectStatus().name()));
    }
}

