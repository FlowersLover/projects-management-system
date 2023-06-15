package com.digitaldesign.murashkina.app.services.unit;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.dto.request.task.SearchTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskStatusRequest;
import com.digitaldesign.murashkina.dto.response.TaskResponse;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.task.Task;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TaskRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import com.digitaldesign.murashkina.services.TaskService;
import com.digitaldesign.murashkina.services.email.EmailServiceImpl;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeDeletedException;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.task.*;
import com.digitaldesign.murashkina.services.mapping.TaskMapper;
import com.digitaldesign.murashkina.services.specifications.TaskSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.digitaldesign.murashkina.app.services.methods.DateFormater.parseDate;
import static com.digitaldesign.murashkina.app.services.unit.TestEntitiesCreator.createTestEmployee;
import static com.digitaldesign.murashkina.app.services.unit.TestEntitiesCreator.createTestProject;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {
    @Spy
    TaskRepository taskRepository;
    @Spy
    EmployeeRepository employeeRepository;
    @Spy
    ProjectRepository projectRepository;
    @Spy
    TeamRepository teamRepository;
    @Spy
    Authentication authentication;
    @Spy
    SecurityContext securityContext;
    @Mock
    TaskMapper taskMapper;
    @Mock
    EmailServiceImpl emailService;
    @Spy
    TaskSpecification taskSpecification;
    @InjectMocks
    TaskService taskService;

    @Test
    public void createTask() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        TaskRequest request = createTestTaskRequest(project, employee);
        TaskResponse response = createTestTaskResponse(task, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByAccount(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any())).thenReturn(true);
        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(response);
        when(taskRepository.save(task)).thenReturn(task);
        TaskResponse acctualresponse = taskService.create(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(task.getId(), acctualresponse.getId());
        Assertions.assertNotNull(acctualresponse.getTaskName());
        Assertions.assertNotNull(acctualresponse.getHoursToCompleteTask());
        Assertions.assertNotNull(acctualresponse.getAuthor());
        Assertions.assertNotNull(acctualresponse.getExecutor());
        Assertions.assertNotNull(acctualresponse.getDescription());
        Assertions.assertNotNull(acctualresponse.getDeadline());
    }

    @Test
    public void createTask_TaskIsNull() {
        Assertions.assertThrows(TaskIsNullException.class, () -> taskService.create(TaskRequest.builder().build()));
    }

    @Test
    public void createTask_employeeDeleted() {
        Employee employee = createTestEmployee("executor1");
        employee.setStatus(EStatus.BLOCKED);
        Project project = createTestProject();
        TaskRequest request = createTestTaskRequest(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Assertions.assertThrows(EmployeeDeletedException.class, () -> taskService.create(request));
    }

    @Test
    public void createTask_EmployeeNotMemberOfTeam() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        TaskRequest request = createTestTaskRequest(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByAccount(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotMemberOfTeamException.class, () -> taskService.create(request));
    }

    @Test
    public void updateTask() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskRequest request = createTestUpdateTaskRequest(employee.getId());
        TaskRequest taskRequest = createTestTaskRequest(project, employee);
        TaskResponse response = createTestTaskResponse(task, employee);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskMapper.toDto(request)).thenReturn(taskRequest);
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByAccount(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any())).thenReturn(true);
        when(taskMapper.toEntity(taskRequest)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(response);
        when(taskRepository.save(task)).thenReturn(task);
        TaskResponse acctualresponse = taskService.update(request, task.getId());
        Assertions.assertEquals(task.getId(), acctualresponse.getId());
        Assertions.assertNotNull(acctualresponse.getTaskName());
        Assertions.assertNotNull(acctualresponse.getHoursToCompleteTask());
        Assertions.assertNotNull(acctualresponse.getAuthor());
        Assertions.assertNotNull(acctualresponse.getExecutor());
        Assertions.assertNotNull(acctualresponse.getDescription());
        Assertions.assertNotNull(acctualresponse.getDeadline());
    }

    @Test
    public void updateTask_requestIsNull() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        Assertions.assertThrows(TaskIsNullException.class,
                () -> taskService.update(UpdateTaskRequest.builder().build(), task.getId()));
    }

    @Test
    public void updateTask_taskNotFound() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskRequest request = createTestUpdateTaskRequest(employee.getId());
        TaskRequest taskRequest = createTestTaskRequest(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskMapper.toDto(request)).thenReturn(taskRequest);
        when(taskRepository.existsById(task.getId())).thenReturn(false);
        Assertions.assertThrows(TaskNotFoundException.class,
                () -> taskService.update(request, task.getId()));
    }

    @Test
    public void updateTask_employeeNotFound() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskRequest request = createTestUpdateTaskRequest(employee.getId());
        TaskRequest taskRequest = createTestTaskRequest(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskMapper.toDto(request)).thenReturn(taskRequest);
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        when(employeeRepository.existsById(employee.getId())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> taskService.update(request, task.getId()));
    }

    @Test
    public void updateTask_employeeDeleted() {
        Employee employee = createTestEmployee("executor1");
        employee.setStatus(EStatus.BLOCKED);
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskRequest request = createTestUpdateTaskRequest(employee.getId());
        TaskRequest taskRequest = createTestTaskRequest(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskMapper.toDto(request)).thenReturn(taskRequest);
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Assertions.assertThrows(EmployeeDeletedException.class,
                () -> taskService.update(request, task.getId()));
    }

    @Test
    public void updateTask_EmployeeNotMemberOfTeam() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskRequest request = createTestUpdateTaskRequest(employee.getId());
        TaskRequest taskRequest = createTestTaskRequest(project, employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskMapper.toDto(request)).thenReturn(taskRequest);
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(employeeRepository.findByAccount(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(teamRepository.existsById(any())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotMemberOfTeamException.class,
                () -> taskService.update(request, task.getId()));
    }

    @Test
    public void updateStatus() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskStatusRequest statusRequest = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS.name());
        TaskResponse response = createTestTaskResponse(task, employee);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(employeeRepository.findByAccount(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any())).thenReturn(true);
        when(taskMapper.toDto(task)).thenReturn(response);
        when(taskRepository.save(task)).thenReturn(task);
        TaskResponse acctualresponse = taskService.updateStatus(statusRequest, task.getId());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(task.getId(), acctualresponse.getId());
        Assertions.assertNotNull(acctualresponse.getTaskName());
        Assertions.assertNotNull(acctualresponse.getHoursToCompleteTask());
        Assertions.assertNotNull(acctualresponse.getAuthor());
        Assertions.assertNotNull(acctualresponse.getExecutor());
        Assertions.assertNotNull(acctualresponse.getDescription());
        Assertions.assertNotNull(acctualresponse.getDeadline());
    }

    @Test
    public void updateStatus_taskNull() {
        Assertions.assertThrows(TaskIsNullException.class, () -> taskService.updateStatus(UpdateTaskStatusRequest.builder().build(), UUID.randomUUID()));
    }

    @Test
    public void updateStatus_taskNotFound() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskStatusRequest statusRequest = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS.name());

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        Assertions.assertThrows(TaskNotFoundException.class, () -> taskService.updateStatus(statusRequest, UUID.randomUUID()));
    }

    @Test
    public void updateStatus_invalidTaskStatus() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskStatusRequest statusRequest = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS.name());
        statusRequest.setStatus("errStatus");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        Assertions.assertThrows(InvalidTaskStatusException.class, () -> taskService.updateStatus(statusRequest, task.getId()));
    }

    @Test
    public void updateStatus_statusNotAviable() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        task.setStatus(TaskStatus.CLOSED);
        UpdateTaskStatusRequest statusRequest = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS.name());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        Assertions.assertThrows(TaskStatusNotAviableException.class, () -> taskService.updateStatus(statusRequest, task.getId()));
    }

    @Test
    public void updateStatus_EmployeeNotMemberOfTeam() {
        Employee employee = createTestEmployee("executor1");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        UpdateTaskStatusRequest statusRequest = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS.name());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.existsById(task.getId())).thenReturn(true);
        when(employeeRepository.findByAccount(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotMemberOfTeamException.class, () -> taskService.updateStatus(statusRequest, task.getId()));
    }

    @Test
    public void searchTask_searchFilterIsNull() {
        Assertions.assertThrows(SearchRequestIsNullException.class, () -> taskService.search(null));
    }

    @Test
    public void searchTask() {
        Employee employee = createTestEmployee("executor1");
        Employee employee2 = createTestEmployee("executor2");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        Task task2 = createTestTask(project, employee2);
        SearchTaskRequest request = SearchTaskRequest.builder().status(TaskStatus.NEW).build();
        TaskResponse response = createTestTaskResponse(task, employee);
        TaskResponse response2 = createTestTaskResponse(task2, employee2);
        when(taskMapper.toDto(task)).thenReturn(response);
        when(taskMapper.toDto(task2)).thenReturn(response2);
        when(taskRepository.findAll(any(Specification.class))).thenReturn(List.of(task, task2));
        List<TaskResponse> acctualresponses = taskService.search(request);
        Assertions.assertEquals(task.getId(), acctualresponses.get(0).getId());
        Assertions.assertEquals(task.getTaskName(), acctualresponses.get(0).getTaskName());
        Assertions.assertEquals(task.getHoursToCompleteTask(), acctualresponses.get(0).getHoursToCompleteTask());
        Assertions.assertEquals(task.getAuthor().getId(), acctualresponses.get(0).getAuthor());
        Assertions.assertEquals(task.getExecutor().getId(), acctualresponses.get(0).getExecutor());
        Assertions.assertEquals(task.getDescription(), acctualresponses.get(0).getDescription());
        Assertions.assertEquals(task.getDeadline(), acctualresponses.get(0).getDeadline());
        Assertions.assertEquals(task2.getId(), acctualresponses.get(1).getId());
        Assertions.assertEquals(task2.getTaskName(), acctualresponses.get(1).getTaskName());
        Assertions.assertEquals(task2.getHoursToCompleteTask(), acctualresponses.get(1).getHoursToCompleteTask());
        Assertions.assertEquals(task2.getAuthor().getId(), acctualresponses.get(1).getAuthor());
        Assertions.assertEquals(task2.getExecutor().getId(), acctualresponses.get(1).getExecutor());
        Assertions.assertEquals(task2.getDescription(), acctualresponses.get(1).getDescription());
        Assertions.assertEquals(task2
                .getDeadline(), acctualresponses.get(1).getDeadline());
    }

    @Test
    public void getTask() {
        Employee employee = createTestEmployee("executor");
        Project project = createTestProject();
        Task task = createTestTask(project, employee);
        TaskResponse response = createTestTaskResponse(task, employee);
        when(taskRepository.existsById(any())).thenReturn(true);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(response);
        TaskResponse acctualResponse = taskService.getTask(task.getId());
        verify(taskRepository, times(1)).findById(any(UUID.class));
        Assertions.assertEquals(task.getId(), acctualResponse.getId());
        Assertions.assertEquals(task.getTaskName(), acctualResponse.getTaskName());
        Assertions.assertEquals(task.getHoursToCompleteTask(), acctualResponse.getHoursToCompleteTask());
        Assertions.assertEquals(task.getAuthor().getId(), acctualResponse.getAuthor());
        Assertions.assertEquals(task.getExecutor().getId(), acctualResponse.getExecutor());
        Assertions.assertEquals(task.getDescription(), acctualResponse.getDescription());
        Assertions.assertEquals(task.getDeadline(), acctualResponse.getDeadline());
    }

    @Test
    public void getTask_taskNotFound() {
        Assertions.assertThrows(TaskNotFoundException.class, () -> taskService.getTask(UUID.randomUUID()));
    }

    private TaskResponse createTestTaskResponse(Task task, Employee employee) {
        return TaskResponse.builder()
                .id(task.getId())
                .taskName("teskName")
                .hoursToCompleteTask(1)
                .description("task description test")
                .deadline(parseDate("2024-01-31 22:01:01"))
                .executor(employee.getId())
                .createdAt(task.getCreatedAt())
                .author(employee.getId())
                .status(TaskStatus.NEW)
                .build();
    }

    private TaskRequest createTestTaskRequest(Project project, Employee employee) {
        return TaskRequest.builder()
                .taskName("teskName")
                .hoursToCompleteTask(1)
                .description("task description test")
                .project(project.getProjectId())
                .deadline(parseDate("2024-01-31 22:01:01"))
                .executor(employee.getId())
                .build();
    }

    private Task createTestTask(Project project, Employee employee) {
        return Task.builder()
                .id(UUID.randomUUID())
                .taskName("teskName")
                .hoursToCompleteTask(1)
                .description("task description test")
                .projectId(project)
                .deadline(parseDate("2024-01-31 22:01:01"))
                .executor(employee)
                .createdAt(new Date())
                .author(employee)
                .status(TaskStatus.NEW)
                .build();
    }

    private UpdateTaskRequest createTestUpdateTaskRequest(UUID executorId) {
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .taskName("teskName")
                .hoursToCompleteTask(1)
                .description("task description test")
                .deadline(parseDate("2024-01-31 22:01:01"))
                .executor(executorId)
                .build();
        return request;
    }
}
