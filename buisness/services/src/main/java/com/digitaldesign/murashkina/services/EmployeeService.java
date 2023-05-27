package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.response.EmployeeResponse;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.request.employee.EmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdateEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdatePasswordRequest;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.services.mapping.EmployeeMapper;
import com.digitaldesign.murashkina.services.specifications.EmployeeSpecification;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeSpecification es;
    private final EmployeeMapper employeeMapper;


    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeSpecification es, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.es = es;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse create(EmployeeRequest createRequest) {
        if (createRequest == null
                || createRequest.getFirstName() == null
                || createRequest.getLastName() == null
                || createRequest.getPassword() == null) {
            throw new IllegalArgumentException("Employee is null");
        }
        Employee employee = employeeMapper.toEntity(createRequest);
        employee.setStatus(EStatus.ACTIVE);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public EmployeeResponse update(UpdateEmployeeRequest updateEmployeeRequest, UUID id) {
        Optional<Employee> oldEmployee = employeeRepository.findById(id);
        if (oldEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
        if (oldEmployee.get().getStatus().name().equals(EStatus.BLOCKED.name())) {
            return null;
        }
        Employee newEmployee = employeeMapper.toEntity(updateEmployeeRequest);
        newEmployee.setId(oldEmployee.get().getId());
        newEmployee.setStatus(oldEmployee.get().getStatus());
        newEmployee.setPassword(oldEmployee.get().getPassword());
        employeeRepository.save(newEmployee);
        return employeeMapper.toDto(newEmployee);
    }

    public EmployeeResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, UUID id) {
        if (!updatePasswordRequest.getPassword().equals(updatePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Password mismatch");
            //return null;
        }
        Employee employee = employeeRepository.findById(id).get();
        employee.setPassword(updatePasswordRequest.getPassword());
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeResponse delete(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
        employeeRepository.setEmployeeStatusById(EStatus.BLOCKED, id);
        return employeeMapper.toDto(employee.get());
    }

    public EmployeeResponse getInfo(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
        return employeeMapper.toDto(employee.get());
    }

    public List<EmployeeResponse> search(SearchEmployeeRequest searchFilter) {
        List<EmployeeResponse> employeeResponses = employeeRepository.findAll(
                es.firstnameLike(searchFilter.getFirstName())
                        .and(es.lastnameLike(searchFilter.getLastName()))
                        .or(es.middlenameLike(searchFilter.getMiddleName()))
                        .or(es.accountEquals(searchFilter.getAccount()))
                        .or(es.emailEquals(searchFilter.getEmail())))
                .stream()
                .map(employee -> employeeMapper.toDto(employee)).collect(Collectors.toList());
        return employeeResponses;
    }


}
