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
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.task.*;
import com.digitaldesign.murashkina.services.mapping.TaskMapper;
import com.digitaldesign.murashkina.services.specifications.TaskSpecification;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final TaskSpecification ts;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository, ProjectRepository projectRepository, TaskSpecification ts, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.ts = ts;
        this.taskMapper = taskMapper;
    }

    private void taskIsNull(TaskRequest taskRequest) {
        if (taskRequest == null
                || taskRequest.getTaskName() == null
                || taskRequest.getHoursToCompleteTask() == null
                || taskRequest.getDeadline() == null) {
            throw new TaskIsNullException();
        }
    }


    public void checkEmployeeInTeam(UUID projectId, String employeeAccount) {
        Optional<Employee> employee = employeeRepository.findByAccount(employeeAccount);
        Optional<Project> project = projectRepository.findById(projectId);
        if (!teamRepository.existsById(TeamId.builder().member(employee.get()).project(project.get()).build())) {
            throw new EmployeeNotMemberOfTeamException();
        }
    }


    public TaskResponse create(TaskRequest taskRequest, String currentUser) {
        //создать задачу проекта может только участник проекта
        taskIsNull(taskRequest);
        EmployeeService.employeeIsDeleted(employeeRepository.findById(taskRequest.getExecutor()).get());
        checkEmployeeInTeam(taskRequest.getProject(), currentUser);
        checkEmployeeInTeam(taskRequest.getProject(), employeeRepository.findById(taskRequest.getExecutor()).get().getAccount());
        Task task = taskMapper.toEntity(taskRequest);
        task.setStatus(TaskStatus.NEW);
        task.setCreatedAt(new Date());
        task.setAuthor(employeeRepository.findByAccount(currentUser).get());
        task.setExecutor(employeeRepository.findById(UUID.fromString(taskRequest.getExecutor().toString())).get());
        task.setProjectId(projectRepository.findById(taskRequest.getProject()).get());
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskResponse update(UpdateTaskRequest updateTaskRequest, UUID id, String currentUser) {
        TaskRequest taskRequest = taskMapper.toDto(updateTaskRequest);
        taskIsNull(taskRequest);
        taskNotFound(id);
        employeeNotFound(taskRequest);
        EmployeeService.employeeIsDeleted(employeeRepository.findById(updateTaskRequest.getExecutor()).get());
        Optional<Task> task = taskRepository.findById(id);
        checkEmployeeInTeam(task.get().getProjectId().getId(), currentUser);
        checkEmployeeInTeam(task.get().getProjectId().getId(), employeeRepository.findById(updateTaskRequest.getExecutor()).get().getAccount());

        Task newTask = taskMapper.toEntity(taskRequest);
        newTask.setLastChanged(new Date());
        newTask.setStatus(task.get().getStatus());
        newTask.setAuthor(employeeRepository.findByAccount(currentUser).get());
        newTask.setExecutor(employeeRepository.findById(updateTaskRequest.getExecutor()).get());
        newTask.setCreatedAt(task.get().getCreatedAt());
        newTask.setProjectId(task.get().getProjectId());
        taskRepository.save(newTask);
        return taskMapper.toDto(newTask);
    }

    private void invalidTaskStatusException(String status) {
        if (!EnumUtils.isValidEnum(TaskStatus.class, status)) {
            throw new InvalidTaskStatusException();
        }
    }

    public TaskResponse updateStatus(UpdateTaskStatusRequest taskStatusRequest, UUID id, String currentUser) {
        taskStatusIsNull(taskStatusRequest);
        taskNotFound(id);
        invalidTaskStatusException(taskStatusRequest.getStatus());
        TaskStatus taskStatus = TaskStatus.valueOf(taskStatusRequest.getStatus());
        statusNotAviable(id, taskStatus);

        Optional<Task> task = taskRepository.findById(id);
        checkEmployeeInTeam(task.get().getProjectId().getId(), currentUser);
        Task newtask = task.get();
        newtask.setStatus(taskStatus);
        newtask.setLastChanged(new Date());
        taskRepository.save(newtask);
        return taskMapper.toDto(newtask);
    }

    public List<TaskResponse> search(SearchTaskRequest searchFilter) {
        List<Task> taskList = taskRepository.findAll(
                ts.getSpecification(searchFilter));
        taskList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                if (t1.getCreatedAt().equals(t2.getCreatedAt())) return 0;
                else if (t1.getCreatedAt().before(t2.getCreatedAt())) return 1;
                else return -1;
            }
        });
        return taskList.stream()
                .map(task -> taskMapper.toDto(task)).collect(Collectors.toList());
    }

    public TaskResponse findById(UUID uuid) {
        taskNotFound(uuid);
        return taskMapper.toDto(taskRepository.findById(uuid).get());
    }

    public boolean existById(UUID id) {
        return taskRepository.existsById(id);
    }

    public boolean taskStatusIsAviable(UUID taskId, TaskStatus status) {
        if (status.getStatusNumber() >= taskRepository.findById(taskId).get().getStatus().getStatusNumber()) {
            return true;
        }
        return false;
    }

    private void taskStatusIsNull(UpdateTaskStatusRequest taskRequest) {
        if (taskRequest == null
                || taskRequest.getStatus() == null) {
            throw new TaskIsNullException();
        }
    }


    private void taskNotFound(UUID id) {
        if (!existById(id)) {
            throw new TaskNotFoundException();
        }
    }

    private void employeeNotFound(TaskRequest taskRequest) {
        if (!employeeRepository.existsById(taskRequest.getExecutor())) {
            throw new EmployeeNotFoundException();
        }
    }

    private void statusNotAviable(UUID id, TaskStatus status) {
        if (!taskStatusIsAviable(id, status)) {
            throw new TaskStatusNotAviableException();
        }
    }
}
