package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.dto.request.task.SearchTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskStatusRequest;
import com.digitaldesign.murashkina.dto.response.TaskResponse;
import com.digitaldesign.murashkina.models.task.Task;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.TaskRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import com.digitaldesign.murashkina.services.mapping.TaskMapper;
import com.digitaldesign.murashkina.services.specifications.TaskSpecification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final TaskSpecification ts;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository, TaskSpecification ts, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.ts = ts;
        this.taskMapper = taskMapper;
    }


    public TaskResponse create(TaskRequest taskRequest) {
        if (taskRequest == null
                || taskRequest.getTaskName() == null
                || taskRequest.getHoursToCompleteTask() == null
                || taskRequest.getAuthor() == null
                || taskRequest.getDeadline() == null) {
            throw new IllegalArgumentException("Task is null");
        }
        if (!teamRepository.findAll().stream().anyMatch(team ->
                team.getTeamId().getMember().getId().equals(taskRequest.getExecutor()))) {
            return null;
        }
        Task task = taskMapper.toEntity(taskRequest);
        task.setStatus(TaskStatus.NEW);
        task.setCreatedAt(new Date());
        task.setAuthor(employeeRepository.findById(UUID.fromString(taskRequest.getAuthor().toString())).get());
        task.setExecutor(employeeRepository.findById(UUID.fromString(taskRequest.getExecutor().toString())).get());
        taskRepository.save(task);
        return taskMapper.toDto(task);
        /*Task newtask = Task.builder()
                .taskName(taskRequest.getTaskName())
                .description(taskRequest.getDescription())
                .executor(employeeRepository.findById(taskRequest.getExecutor()).get())
                .hoursToCompleteTask(taskRequest.getHoursToCompleteTask())
                .status(TaskStatus.NEW)
                .author(employeeRepository.findById(taskRequest.getAuthor()).get())
                .lastChanged(new Date())
                .deadline(taskRequest.getDeadline())
                .createdAt(new Date())
                .build();*/
        //taskRepository.save(newtask);
        //return newtask;
    }

    public TaskResponse update(TaskRequest taskRequest, UUID id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }
        if (!teamRepository.findAll().stream().anyMatch(team -> team.getTeamId().getMember().getId().equals(taskRequest.getExecutor()))) {
            return null;
        }
        Task newTask = taskMapper.toEntity(taskRequest);
        newTask.setLastChanged(new Date());
        newTask.setStatus(task.get().getStatus());
        newTask.setAuthor(employeeRepository.findById(taskRequest.getAuthor()).get());
        newTask.setExecutor(employeeRepository.findById(taskRequest.getExecutor()).get());
        taskRepository.save(newTask);
        return taskMapper.toDto(newTask);
        /*Task task = taskRepository.findById(id).get();
        task.setAuthor(employeeRepository.findById(taskRequest.getAuthor()).get());
        task.setTaskName(taskRequest.getTaskName());
        task.setDescription(taskRequest.getDescription());
        task.setHoursToCompleteTask(taskRequest.getHoursToCompleteTask());
        task.setDeadline(taskRequest.getDeadline());
        task.setExecutor(employeeRepository.findById(taskRequest.getExecutor()).get());
        task.setLastChanged(new Date());
        taskRepository.save(task);
        return task;*/
    }

    public TaskResponse updateStatus(UpdateTaskStatusRequest taskStatusRequest, UUID id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }
        if (taskStatusRequest.getStatus().getStatusNumber() < task.get().getStatus().getStatusNumber()) {
            return null;
        }
        Task newtask = task.get();
        newtask.setStatus(taskStatusRequest.getStatus());
        taskRepository.save(newtask);
        return taskMapper.toDto(newtask);
    }

    public List<TaskResponse> search(SearchTaskRequest searchFilter) {

        List<Task> taskList = taskRepository.findAll(
                ts.executorEqual(searchFilter.getExecutor())
                        .or(ts.authorEqual(searchFilter.getAuthor()))
                        .or(ts.createdAtEqual(searchFilter.getCreatedAt()))
                        .or(ts.deadlineEqual(searchFilter.getDeadline()))
                        .or(ts.tasknameEqual(searchFilter.getTaskName()))
                        .or(ts.statusEqual(searchFilter.getStatus())));
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
}
