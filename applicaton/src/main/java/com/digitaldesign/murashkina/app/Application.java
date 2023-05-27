package com.digitaldesign.murashkina.app;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.dto.request.employee.EmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdateEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdatePasswordRequest;
import com.digitaldesign.murashkina.dto.request.project.ProjectRequest;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.dto.request.project.UpdateProjectStatus;
import com.digitaldesign.murashkina.dto.request.task.SearchTaskRequest;
import com.digitaldesign.murashkina.dto.request.task.TaskRequest;
import com.digitaldesign.murashkina.dto.request.task.UpdateTaskStatusRequest;
import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.dto.response.EmployeeResponse;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.dto.response.TaskResponse;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.TaskRepository;
import com.digitaldesign.murashkina.services.EmployeeService;
import com.digitaldesign.murashkina.services.ProjectService;
import com.digitaldesign.murashkina.services.TaskService;
import com.digitaldesign.murashkina.services.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@SpringBootApplication
@ComponentScan({"com.digitaldesign.murashkina.dto",
        "com.digitaldesign.murashkina.services",
        "com.digitaldesign.murashkina.repositories"})
@EntityScan("com.digitaldesign.murashkina.models")
@EnableJpaRepositories("com.digitaldesign.murashkina.repositories")
public class Application {
    private final TaskRepository taskRepository;

    public Application(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            EmployeeService employeeService,
            ProjectService projectService,
            TaskService taskService,
            TeamService teamService) {
        return args -> {
//--------------EMPLOYEE----
            //Создание сотрудника
            /*EmployeeRequest employee = EmployeeRequest.builder()
                    .firstName("kkkkk")
                    .lastName("Bamjtot")
                    .middleName("Bebrik")
                    .account("bebra888")
                    .email("frosia@mail.com")
                    .position("manager")
                    .password("qwerty123")
                    .build();
            EmployeeResponse e = employeeService.create(employee);
            System.out.println(e.toString());*/

            //Изменение сотрудника
            /*UpdateEmployeeRequest request = UpdateEmployeeRequest
                    .builder()
                    .firstName("olya")
                    .lastName("lalal")
                    .middleName("frfr")
                    .email("mrozl@histats.com")
                    .account("bebt")
                    .position("VP Marketing")
                    .build();
            EmployeeResponse employeeUpd = employeeService.update(request, UUID.fromString("c4fe7113-7bf2-4b0c-abe8-1cb98be58f19"));
            System.out.println(employeeUpd.toString());*/

            //Изменение пароля
            /*UpdatePasswordRequest passwordRequest = UpdatePasswordRequest.builder()
                    .password("123456778")
                    .confirmPassword("123456778")
                    .build();
            EmployeeResponse employee1 = employeeService.updatePassword(passwordRequest, UUID.fromString("c4fe7113-7bf2-4b0c-abe8-1cb98be58f19"));
            System.out.println(employee1.toString());*/

            //Удаление сотрудника
            /*employeeService.delete(employee1.getId());*/

            //Поиск сотрудников
            /*SearchEmployeeRequest employeeRequest = SearchEmployeeRequest.builder()
                    //.firstName("Kacey")
                    .middleName("Garlett")
                    .account("klavigne9")
                    .email("frosia@mail.com")
                    .build();
            List<EmployeeResponse> employeeList = employeeService.search(employeeRequest);
            for (EmployeeResponse e : employeeList) {
                System.out.println(e.toString());
            }*/

//---------PROJECT methods--------------------------------------------------------
            //Создание проекта
            /*ProjectRequest projectRequest = ProjectRequest.builder()
                    .projectName("projname123456")
                    .description("описание2")
                    .build();
            ProjectResponse project = projectService.create(projectRequest);
            System.out.println(project.toString());*/
            //Изменение проекта
           /* ProjectRequest projectRequest2 = ProjectRequest.builder()
                    .projectName("projname3")
                    .description("description3")
                    .build();
            ProjectResponse project1 = projectService.update(projectRequest2,
                    UUID.fromString("283a0b12-dad4-45e1-a0d8-a3f63fc1d54c"));
            System.out.println(project1.toString());*/

            //Изменение статуса проекта
            /*UpdateProjectStatus updateProjectStatus = UpdateProjectStatus.builder()
                    .status(ProjStatus.COMPLETED)
                    .build();
            ProjectResponse project2 = projectService.updateStatus(updateProjectStatus.getStatus(), UUID.fromString("9e82845b-dd41-4b17-bfb6-1131c4940d2c"));
            projectService.updateStatus(ProjStatus.TEST, UUID.fromString("7f2c594b-a432-4620-ba72-92f48e2ac94b"));
*/
            //Поиск проекта
            /*List<ProjStatus> projStatuses = new ArrayList<>();
            projStatuses.add(ProjStatus.TEST);
            SearchProjRequest request = SearchProjRequest.builder()
                    .statuses(projStatuses)
                    .projectName("projname2")
                    .build();
            List<ProjectResponse> search = projectService.search(request);
            for (ProjectResponse p : search) {
                System.out.println(p.toString());
            }*/
//---------TASK METHODS----------------------------
            //Создание задачи
            Calendar calendar = Calendar.getInstance();
            calendar.set(2022, Calendar.JUNE, 10, 5, 12, 12);
            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss", Locale.getDefault());
            Date date = calendar.getTime();
            Timestamp timestamp = new java.sql.Timestamp(date.getTime());

            TaskRequest taskRequest = TaskRequest.builder()
                    .taskName("taskname1")
                    .description("description123")
                    .hoursToCompleteTask(260)
                    .deadline(timestamp)
                    .author(UUID.fromString("9dac079a-8111-4966-ace1-c22502a67a1c"))
                    .executor(UUID.fromString("4833f7a0-277e-4d41-86fd-efe83dcae9b0"))
                    .build();
            TaskRequest taskRequest2 = TaskRequest.builder()
                    .taskName("taskname2")
                    .description("description2")
                    .hoursToCompleteTask(22)
                    .deadline(timestamp)
                    .author(UUID.fromString("ce1f3c3b-c633-46f6-988d-2800eed796a0"))
                    .executor(UUID.fromString("29dab1ce-1480-464c-8341-4d9c6545c923"))
                    .build();
            //Создание задачи
            //TaskResponse task = taskService.create(taskRequest);
            //System.out.println(task.toString());

            //Изменение задачи
            /*TaskResponse update = taskService.update(taskRequest2, UUID.fromString("a80f745c-1dad-4680-8ff1-abe30a088e13"));
            System.out.println(update);*/

            //Изменение статуса
            /*UpdateTaskStatusRequest statusRequest = UpdateTaskStatusRequest.builder().status(TaskStatus.IN_PROGRESS).build();

            TaskResponse taskResponse = taskService.updateStatus(statusRequest, UUID.fromString("03baa51d-3056-423c-9270-2461992dd2f2"));
            System.out.println(taskResponse.toString());*/

            //Поиск задачи

            /*SearchTaskRequest searchTaskRequest = SearchTaskRequest.builder()
                    .taskName("taskname1")
                    //.executor(UUID.fromString("c876808f-71d7-4780-b4e0-e2dfc3fc5224"))
                    .build();
            List<TaskResponse> taskList = taskService.search(searchTaskRequest);
            for (TaskResponse p : taskList) {
                System.out.println(p.toString());
            }*/
            //--TEAM--------------

            //Создание участника
            TeamDto teamDto = TeamDto.builder()
                    .member(UUID.fromString("ee79c471-432c-44c4-a26a-cd86286c0eac"))
                    .role(TeamRole.ANALYST)
                    .project(UUID.fromString("9e82845b-dd41-4b17-bfb6-1131c4940d2c")).build();
            TeamDto member = teamService.createMember(teamDto);
            System.out.println(member);

            //Получить всех
            List<TeamDto> all = teamService.getAll();
            for (TeamDto p : all) {
                System.out.println(p.toString());
            }
            TeamDto teamDto1 = teamService.deleteMember(teamDto);
            System.out.println(teamDto1.toString());
        };
    }

}-
