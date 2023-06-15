package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.dto.request.task.SearchTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskStatusRequest;
import com.digitaldesign.murashkina.dto.response.TaskResponse;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.task.Task;
import com.digitaldesign.murashkina.models.team.TeamId;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TaskRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import com.digitaldesign.murashkina.services.email.EmailDetails;
import com.digitaldesign.murashkina.services.email.EmailServiceImpl;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.task.*;
import com.digitaldesign.murashkina.services.mapping.TaskMapper;
import com.digitaldesign.murashkina.services.specifications.TaskSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final TaskSpecification ts;
    private final TaskMapper taskMapper;
    private final EmailServiceImpl emailServiceImpl;


    public TaskService(TaskRepository taskRepository,
                       EmployeeRepository employeeRepository,
                       TeamRepository teamRepository,
                       ProjectRepository projectRepository,
                       TaskSpecification ts, TaskMapper taskMapper, EmailServiceImpl emailServiceImpl) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.ts = ts;
        this.taskMapper = taskMapper;
        this.emailServiceImpl = emailServiceImpl;
    }

    private void sendEmailAssignExecutor(TaskResponse taskResponse) {
        Employee executor = employeeRepository.findById(taskResponse.getExecutor()).get();
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", executor.getFirstName());
            model.put("taskName", taskResponse.getTaskName());
            model.put("createdAt", taskResponse.getCreatedAt());
            model.put("author", taskResponse.getAuthor());
            EmailDetails details = EmailDetails.builder().to(executor.getEmail())
                    .subject("Вы назанчены исполнителем задачи").templateLocation("letter-template").context(model).build();
            emailServiceImpl.sendMail(details);
        } catch (MailException mailException) {
            log.error("Error while sending out email..{}", mailException.getStackTrace());
        }
    }

    public TaskResponse create(TaskRequest taskRequest) {
        log.debug("Task create started");
        taskIsNull(taskRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        EmployeeService.employeeDeleted(employeeRepository.findById(taskRequest.getExecutor()).get());
        checkEmployeeInTeam(taskRequest.getProject(), currentUser);
        checkEmployeeInTeam(taskRequest.getProject(), employeeRepository.findById(taskRequest.getExecutor()).get().getAccount());
        Task task = taskMapper.toEntity(taskRequest);
        task.setStatus(TaskStatus.NEW);
        task.setCreatedAt(new Date());
        task.setAuthor(employeeRepository.findByAccount(currentUser).get());
        task.setExecutor(employeeRepository.findById(UUID.fromString(taskRequest.getExecutor().toString())).get());
        task.setProjectId(projectRepository.findById(taskRequest.getProject()).get());
        taskRepository.save(task);
        TaskResponse dto = taskMapper.toDto(task);
        sendEmailAssignExecutor(dto);
        return dto;
    }

    public TaskResponse update(UpdateTaskRequest updateTaskRequest, UUID id) {
        log.debug("Task change started");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        TaskRequest taskRequest = taskMapper.toDto(updateTaskRequest);
        taskIsNull(taskRequest);
        taskNotFound(id);
        employeeNotFound(taskRequest);
        EmployeeService.employeeDeleted(employeeRepository.findById(updateTaskRequest.getExecutor()).get());
        Optional<Task> task = taskRepository.findById(id);
        checkEmployeeInTeam(task.get().getProjectId().getProjectId(), currentUser);
        checkEmployeeInTeam(task.get().getProjectId().getProjectId(), employeeRepository.findById(updateTaskRequest.getExecutor()).get().getAccount());
        Task newTask = taskMapper.toEntity(taskRequest);
        newTask.setLastChanged(new Date());
        newTask.setStatus(task.get().getStatus());
        newTask.setAuthor(employeeRepository.findByAccount(currentUser).get());
        newTask.setExecutor(employeeRepository.findById(updateTaskRequest.getExecutor()).get());
        newTask.setCreatedAt(task.get().getCreatedAt());
        newTask.setProjectId(task.get().getProjectId());
        taskRepository.save(newTask);
        TaskResponse dto = taskMapper.toDto(newTask);
        if (!task.get().getExecutor().getId().equals(updateTaskRequest.getExecutor())) {
            sendEmailAssignExecutor(dto);
        }
        return dto;
    }

    public TaskResponse updateStatus(UpdateTaskStatusRequest taskStatusRequest, UUID id) {
        log.debug("Task status change started");
        taskStatusIsNull(taskStatusRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        taskNotFound(id);
        invalidTaskStatus(taskStatusRequest.getStatus());
        TaskStatus taskStatus = TaskStatus.valueOf(taskStatusRequest.getStatus());
        statusNotAviable(id, taskStatus);

        Optional<Task> task = taskRepository.findById(id);
        checkEmployeeInTeam(task.get().getProjectId().getProjectId(), currentUser);
        Task newtask = task.get();
        newtask.setStatus(taskStatus);
        newtask.setLastChanged(new Date());
        taskRepository.save(newtask);
        return taskMapper.toDto(newtask);
    }

    public List<TaskResponse> search(SearchTaskRequest searchFilter) {
        log.debug("Task search started");
        searchFilterIsNull(searchFilter);
        List<Task> taskList = taskRepository.findAll(
                ts.getSpecification(searchFilter));
        List<Task> newList = new ArrayList(Collections.unmodifiableList(taskList));
        newList.sort((t1, t2) -> {
            if (t1.getCreatedAt().equals(t2.getCreatedAt())) return 0;
            else if (t1.getCreatedAt().before(t2.getCreatedAt())) return 1;
            else return -1;
        });
        return taskList.stream()
                .map(taskMapper::toDto).collect(Collectors.toList());
    }

    private void searchFilterIsNull(SearchTaskRequest searchFilter) {
        if (searchFilter == null) {
            log.warn("Search Filter Is Null");
            throw new SearchRequestIsNullException();
        }
    }

    public TaskResponse getTask(UUID uuid) {
        taskNotFound(uuid);
        return taskMapper.toDto(taskRepository.findById(uuid).get());
    }

    private void invalidTaskStatus(String status) {
        if (!EnumUtils.isValidEnum(TaskStatus.class, status)) {
            log.warn("Invalid Task Status");
            throw new InvalidTaskStatusException();
        }
    }

    public boolean taskStatusIsAviable(UUID taskId, TaskStatus status) {
        return status.getStatusNumber() >= taskRepository.findById(taskId).get().getStatus().getStatusNumber();
    }

    private void taskStatusIsNull(UpdateTaskStatusRequest taskRequest) {
        if (taskRequest == null
                || taskRequest.getStatus() == null) {
            log.warn("Task Status Is Null");
            throw new TaskIsNullException();
        }
    }

    public void checkEmployeeInTeam(UUID projectId, String employeeAccount) {
        Optional<Employee> employee = employeeRepository.findByAccount(employeeAccount);
        Optional<Project> project = projectRepository.findById(projectId);
        if (!teamRepository.existsById(TeamId.builder().member(employee.get()).project(project.get()).build())) {
            log.warn("Employee Not Member Of Team");
            throw new EmployeeNotMemberOfTeamException();
        }
    }

    private void taskNotFound(UUID id) {
        if (!taskRepository.existsById(id)) {
            log.warn("Task Not Found");
            throw new TaskNotFoundException();
        }
    }

    private void employeeNotFound(TaskRequest taskRequest) {
        if (!employeeRepository.existsById(taskRequest.getExecutor())) {
            log.warn("Employee Not Found");
            throw new EmployeeNotFoundException();
        }
    }

    private void statusNotAviable(UUID id, TaskStatus status) {
        if (!taskStatusIsAviable(id, status)) {
            log.warn("Status Not Aviable");
            throw new TaskStatusNotAviableException();
        }
    }

    private void taskIsNull(TaskRequest taskRequest) {
        if (taskRequest == null
                || taskRequest.getTaskName() == null
                || taskRequest.getHoursToCompleteTask() == null
                || taskRequest.getDeadline() == null) {
            log.warn("Task is null");
            throw new TaskIsNullException();
        }
    }
}
