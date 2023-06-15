package com.digitaldesign.murashkina.app.services.integretion.controllers;

import com.digitaldesign.murashkina.app.services.integretion.BaseTest;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.dto.request.team.DeleteMember;
import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TeamControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TeamRepository teamRepository;

    @AfterEach
    public void resetDb() {
        teamRepository.deleteAll();
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void createMember_stausCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        TeamDto request = TeamDto.builder().role(TeamRole.ANALYST.name())
                .member(employee.getId())
                .project(project.getProjectId()).build();
        mockMvc.perform(
                        post("/team")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.project").value(request.getProject().toString()))
                .andExpect(jsonPath("$.role").value(request.getRole()))
                .andExpect(jsonPath("$.member").value(request.getMember().toString()));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void deleteMember_stausOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team teamTest = createTeamTest(employee, project);
        DeleteMember request = DeleteMember.builder()
                .member(employee.getId())
                .project(project.getProjectId()).build();
        mockMvc.perform(
                        delete("/team")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value(request.getProject().toString()))
                .andExpect(jsonPath("$.role").value(teamTest.getRole().name()))
                .andExpect(jsonPath("$.member").value(request.getMember().toString()));
    }

    private Employee createTestEmployee(String account) {
        Employee employee = Employee.builder()
                .account(account)
                .lastName("Test")
                .firstName("Test")
                .password("password12")
                .status(EStatus.ACTIVE)
                .position("tester")
                .build();
        return employeeRepository.save(employee);
    }

    private Project createTestProject() {
        Project project = Project.builder().projectStatus(ProjStatus.DRAFT)
                .description("lalalal lal")
                .projectName("testproject").build();
        return projectRepository.save(project);
    }

    private Team createTeamTest(Employee employee, Project project) {
        TeamId teamId = TeamId.builder().member(employee).project(project).build();
        Team team = Team.builder().role(TeamRole.ANALYST).teamId(teamId).build();
        return teamRepository.save(team);
    }
}
