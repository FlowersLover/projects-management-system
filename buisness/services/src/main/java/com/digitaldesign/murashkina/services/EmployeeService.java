package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.enums.ERole;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.request.employee.EmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdateEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdatePasswordRequest;
import com.digitaldesign.murashkina.dto.response.EmployeeResponse;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.services.exceptions.employee.*;
import com.digitaldesign.murashkina.services.mapping.EmployeeMapper;
import com.digitaldesign.murashkina.services.specifications.EmployeeSpecification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSpecification es;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;


    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeSpecification es, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.es = es;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public EmployeeResponse create(EmployeeRequest createRequest) {
        employeeIsNull(createRequest);
        accountAlreadyExist(createRequest);
        Employee employee = employeeMapper.toEntity(createRequest);
        employee.setStatus(EStatus.ACTIVE);
        employee.setPassword(passwordEncoder.encode(createRequest.getPassword()));
        employee.setRole(ERole.ROLE_USER);
        Employee newEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(newEmployee);

    }

    public EmployeeResponse update(UpdateEmployeeRequest updateEmployeeRequest, UUID id) {
        accessToResourceDenied(id);
        accountAlreadyExistUpdate(updateEmployeeRequest, id);
        Employee oldEmployee = employeeRepository.findById(id).get();
        emploeeNotFound(id);
        employeeDeleted(oldEmployee);
        Employee newEmployee = employeeMapper.toEntity(updateEmployeeRequest);
        newEmployee.setId(oldEmployee.getId());
        newEmployee.setStatus(oldEmployee.getStatus());
        newEmployee.setPassword(oldEmployee.getPassword());
        newEmployee.setRole(oldEmployee.getRole());
        Employee save = employeeRepository.save(newEmployee);
        return employeeMapper.toDto(save);
    }

    public EmployeeResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, UUID id) {
        emploeeNotFound(id);
        accessToResourceDenied(id);
        Employee employee = employeeRepository.findById(id).get();
        employeeDeleted(employee);
        passwordsMatch(updatePasswordRequest);
        employee.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeResponse delete(UUID id) {
        emploeeNotFound(id);
        employeeRepository.setEmployeeStatusById(EStatus.BLOCKED, id);
        Employee employee = employeeRepository.findById(id).get();
        return employeeMapper.toDto(employee);
    }

    public EmployeeResponse getEmployee(UUID id) {
        emploeeNotFound(id);
        Optional<Employee> employee = employeeRepository.findById(id);
        return employeeMapper.toDto(employee.get());
    }

    public List<EmployeeResponse> search(SearchEmployeeRequest searchFilter) {
        List<EmployeeResponse> employeeResponses = employeeRepository.findAll(
                        es.getSpecification(searchFilter))
                .stream()
                .map(employee -> employeeMapper.toDto(employee)).collect(Collectors.toList());
        return employeeResponses;
    }

    private void accessToResourceDenied(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!getEmployee(id).getAccount().equals(authentication.getName())
                && !findByAccount(authentication.getName()).getRole().equals(ERole.ROLE_ADMIN)) {
            throw new AccessToResourceDeniedException();
        }
    }

    private void employeeIsNull(EmployeeRequest createRequest) {
        if (createRequest == null
                || createRequest.getFirstName() == null
                || createRequest.getLastName() == null
                || createRequest.getPassword() == null) {
            throw new EmployeeIsNullException();
        }
    }

    private void accountAlreadyExist(EmployeeRequest createRequest) {
        if (employeeRepository.existsEmployeeByAccount(createRequest.getAccount())) {
            throw new AccountAlreadyExistException();
        }
    }

    private void accountAlreadyExistUpdate(UpdateEmployeeRequest updateEmployeeRequest, UUID id) {
        if (employeeRepository.existsEmployeeByAccount(updateEmployeeRequest.getAccount())
                && !updateEmployeeRequest.getAccount().equals(getEmployee(id).getAccount())) {
            throw new AccountAlreadyExistException();
        }
    }

    private void emploeeNotFound(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }
    }

    private void passwordsMatch(UpdatePasswordRequest updatePasswordRequest) {
        if (!updatePasswordRequest.getPassword().equals(updatePasswordRequest.getConfirmPassword())) {
            throw new PasswordsMismatchException();
        }
    }

    public static void employeeDeleted(Employee employee) {
        if (employee.getStatus().name().equals(EStatus.BLOCKED.name())) {
            throw new EmployeeDeletedException();
        }
    }


    public Employee findByAccount(String account) {
        Optional<Employee> employee = employeeRepository.findByAccount(account);
        return employee.get();
    }

}
