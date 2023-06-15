package com.digitaldesign.murashkina.app.services.integretion.controllers;

import com.digitaldesign.murashkina.app.services.integretion.BaseTest;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.request.employee.AuthRequest;
import com.digitaldesign.murashkina.dto.request.employee.EmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdateEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdatePasswordRequest;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.services.mapping.EmployeeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeMapper employeeMapper;

    @AfterEach
    public void resetDb() {
        employeeRepository.deleteAll();
    }
   /* @Test
    public void authenticateAndGetToken_statusOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthRequest authRequest = AuthRequest.builder().account("admin").password("admin").build();

        mockMvc.perform(
                        post("/employee/authenticate")
                                .content(mapper.writeValueAsString(authRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").isNotEmpty());

    }*/

    @Test
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EmployeeRequest request = EmployeeRequest.builder()
                .position("tester")
                .firstName("name")
                .lastName("last")
                .account("employee123")
                .password("pass12345").build();
        mockMvc.perform(
                        post("/employee/new")
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.position").value("tester"))
                .andExpect(jsonPath("$.account").value("employee123"))
                .andExpect(jsonPath("$.lastName").value("last"))
                .andExpect(jsonPath("$.firstName").value("name"))
                .andExpect(jsonPath("$.middleName").isEmpty())
                .andExpect(jsonPath("$.email").isEmpty())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }


    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void updateEmployee_statusOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = createTestEmployee("user");
        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .position("worker")
                .firstName("new name")
                .lastName("new last")
                .middleName("middle")
                .account("employee1")
                .email("user1@mail.com")
                .build();
        mockMvc.perform(
                        put("/employee/{employeeId}", employee.getId())
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId().toString()))
                .andExpect(jsonPath("$.position").value(request.getPosition()))
                .andExpect(jsonPath("$.lastName").value(request.getLastName()))
                .andExpect(jsonPath("$.firstName").value(request.getFirstName()))
                .andExpect(jsonPath("$.middleName").value(request.getMiddleName()))
                .andExpect(jsonPath("$.account").value(request.getAccount()))
                .andExpect(jsonPath("$.email").value(request.getEmail()));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void updatePassword_statusOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = createTestEmployee("user");
        UpdatePasswordRequest request = UpdatePasswordRequest.builder().password("qwerty122").confirmPassword("qwerty122").build();
        mockMvc.perform(
                        put("/employee/password/{employeeId}", employee.getId())
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId().toString()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.middleName").value(employee.getMiddleName()))
                .andExpect(jsonPath("$.account").value(employee.getAccount()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.status").value(employee.getStatus().name()));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void deleteEmployee_statusOk() throws Exception {
        Employee employee = createTestEmployee("user");
        mockMvc.perform(
                        delete("/employee/{employeeId}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId().toString()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.middleName").value(employee.getMiddleName()))
                .andExpect(jsonPath("$.account").value(employee.getAccount()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.status").value(EStatus.BLOCKED.name()));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void getEmployeeInfo() throws Exception {
        Employee employee = createTestEmployee("user");
        mockMvc.perform(
                        get("/employee/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId().toString()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.middleName").value(employee.getMiddleName()))
                .andExpect(jsonPath("$.account").value(employee.getAccount()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.status").value(employee.getStatus().name()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void searchProjects() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = employeeRepository.save(Employee.builder()
                .position("tester")
                .firstName("nameone")
                .lastName("last")
                .account("employee123")
                .password("pass12345")
                .status(EStatus.ACTIVE).build());
        Employee employee2 = employeeRepository.save(Employee.builder()
                .position("tester")
                .firstName("nametwo")
                .lastName("last")
                .account("user2")
                .password("pass12345")
                .status(EStatus.ACTIVE).build());
        Employee employee3 = employeeRepository.save(Employee.builder()
                .position("tester")
                .firstName("nametwo")
                .lastName("last1")
                .account("user3")
                .password("pass12345")
                .email("user3@maio.com")
                .status(EStatus.ACTIVE).build());

        mockMvc.perform(get("/employee/search")
                        .param("lastName", employee.getLastName())
                        .param("firstName", employee.getFirstName()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(employeeMapper.toDto(employee)))));
        mockMvc.perform(get("/employee/search")
                        .param("email", employee3.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(employeeMapper.toDto(employee3)))));
        mockMvc.perform(get("/employee/search")
                        .param("firstName", employee2.getFirstName()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Arrays.asList(employeeMapper.toDto(employee2),
                        employeeMapper.toDto(employee3)))));
    }



    @Test
    public void authenticateAndGetToken_statusBadRequest_Invalidlogin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthRequest authRequest = AuthRequest.builder().account("invalidlogin").password("admin").build();
        mockMvc.perform(
                        post("/employee/authenticate")
                                .content(mapper.writeValueAsString(authRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Неправильный логин или пароль"));
    }

    private Employee createTestEmployee(String account) {
        Employee employee = Employee.builder()
                .position("tester")
                .firstName("name")
                .lastName("last")
                .account(account)
                .password("pass12345")
                .status(EStatus.ACTIVE).build();
        return employeeRepository.save(employee);
    }
}
