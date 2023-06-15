package com.digitaldesign.murashkina.app.services.integretion.controllers;

import com.digitaldesign.murashkina.app.services.integretion.BaseTest;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskStatusRequest;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.task.Task;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TaskRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import com.digitaldesign.murashkina.services.mapping.TaskMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import static com.digitaldesign.murashkina.app.services.methods.DateFormater.formatDate;
import static com.digitaldesign.murashkina.app.services.methods.DateFormater.parseDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class TaskControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TaskMapper taskMapper;

    @BeforeEach
    @AfterEach
    public void resetDb() {
        taskRepository.deleteAll();
        teamRepository.deleteAll();
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void createTask_statusCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee executor = createTestEmployee("executor");
        Employee user = createTestEmployee("user");
        Project testProject = createTestProject();
        createTeamTest(executor, testProject);
        createTeamTest(user, testProject);
        TaskRequest request = TaskRequest.builder().taskName("teskName")
                .hoursToCompleteTask(1)
                .description("task description test")
                .project(testProject.getProjectId())
                .deadline(parseDate("2024-01-31 22:01:01"))
                .executor(executor.getId())
                .build();
        mockMvc.perform(
                        post("/task")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.taskName").value(request.getTaskName()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.executor").value(request.getExecutor().toString()))
                .andExpect(jsonPath("$.hoursToCompleteTask").value(request.getHoursToCompleteTask()))
                .andExpect(jsonPath("$.author").isNotEmpty())
                .andExpect(jsonPath("$.deadline").value(formatDate(request.getDeadline())))
                .andExpect(jsonPath("$.lastChanged").isEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.status").value(TaskStatus.NEW.name()));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void updateTask_statusOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Task task = createTaskTest();
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .taskName("newteskName")
                .hoursToCompleteTask(22)
                .description("updatetask description test")
                .deadline(parseDate("2024-08-21 22:01:01"))
                .executor(task.getExecutor().getId())
                .build();
        mockMvc.perform(
                        put("/task/{tasId}", task.getId())
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.taskName").value(request.getTaskName()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.executor").value(request.getExecutor().toString()))
                .andExpect(jsonPath("$.hoursToCompleteTask").value(request.getHoursToCompleteTask()))
                .andExpect(jsonPath("$.author").isNotEmpty())
                .andExpect(jsonPath("$.deadline").value(formatDate(request.getDeadline())))
                .andExpect(jsonPath("$.lastChanged").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.status").value(TaskStatus.NEW.name()));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void updateStatus_statusOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Task task = createTaskTest();
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS.name());
        mockMvc.perform(
                        put("/task/status/{tasId}", task.getId())
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.taskName").value(task.getTaskName()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.executor").value(task.getExecutor().getId().toString()))
                .andExpect(jsonPath("$.hoursToCompleteTask").value(task.getHoursToCompleteTask()))
                .andExpect(jsonPath("$.author").isNotEmpty())
                .andExpect(jsonPath("$.deadline").value(formatDate(task.getDeadline())))
                .andExpect(jsonPath("$.lastChanged").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.status").value(request.getStatus()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getTask_statusOk() throws Exception {
        Task task = createTaskTest();
        mockMvc.perform(
                        get("/task/{id}", task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId().toString()))
                .andExpect(jsonPath("$.taskName").value(task.getTaskName()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.executor").value(task.getExecutor().getId().toString()))
                .andExpect(jsonPath("$.hoursToCompleteTask").value(task.getHoursToCompleteTask()))
                .andExpect(jsonPath("$.author").value(task.getAuthor().getId().toString()))
                .andExpect(jsonPath("$.deadline").value(formatDate(task.getDeadline())))
                .andExpect(jsonPath("$.lastChanged").isEmpty())
                .andExpect(jsonPath("$.createdAt").value(formatDate(task.getCreatedAt())))
                .andExpect(jsonPath("$.status").value(task.getStatus().name()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void searchTask_statusOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee executor = createTestEmployee("executor");
        Employee executor2 = createTestEmployee("executor2");
        Employee user = createTestEmployee("user");
        Project testProject = createTestProject();
        createTeamTest(executor, testProject);
        createTeamTest(user, testProject);
        Task task1 = taskRepository.save(Task.builder()
                .taskName("one")
                .hoursToCompleteTask(1)
                .description("task description test")
                .author(user)
                .deadline(parseDate("2024-08-21 22:01:01"))
                .createdAt(parseDate("2023-02-02 22:01:01"))
                .executor(executor)
                .status(TaskStatus.NEW)
                .projectId(testProject).build());
        Task task2 = taskRepository.save(Task.builder()
                .taskName("one")
                .hoursToCompleteTask(1)
                .description("task description test")
                .author(user)
                .deadline(parseDate("2024-08-21 22:01:01"))
                .createdAt(parseDate("2023-02-02 22:01:01"))
                .executor(executor2).status(TaskStatus.IN_PROGRESS)
                .projectId(testProject).build());
        Task task3 = taskRepository.save(Task.builder().taskName("three")
                .hoursToCompleteTask(2).description("task description test").author(user)
                .deadline(parseDate("2024-08-21 22:01:01")).createdAt(parseDate("2023-02-02 22:01:01"))
                .executor(executor).status(TaskStatus.IN_PROGRESS)
                .projectId(testProject).build());
        mockMvc.perform(get("/task/search").param("status", task2.getStatus().name()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(taskMapper.toDto(task2), taskMapper.toDto(task3)))));
        mockMvc.perform(get("/task/search")
                        .param("taskName", task2.getTaskName())
                        .param("executor", task3.getExecutor().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(taskMapper.toDto(task1)))));
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

    private Task createTaskTest() throws ParseException {
        Employee executor = createTestEmployee("executor");
        Employee user = createTestEmployee("user");
        Project testProject = createTestProject();
        createTeamTest(executor, testProject);
        createTeamTest(user, testProject);
        Task task = Task.builder()
                .taskName("teskName")
                .hoursToCompleteTask(1)
                .description("task description test")
                .author(user)
                .deadline(parseDate("2024-01-31 22:01:01"))
                .createdAt(new Date())
                .executor(executor)
                .status(TaskStatus.NEW)
                .projectId(testProject)
                .build();
        Task savedTask = taskRepository.save(task);
        return savedTask;
    }


}