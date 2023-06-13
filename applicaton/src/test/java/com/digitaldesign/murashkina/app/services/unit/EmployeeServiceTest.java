package com.digitaldesign.murashkina.app.services.unit;

import com.digitaldesign.murashkina.dto.enums.ERole;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.request.employee.EmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdateEmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdatePasswordRequest;
import com.digitaldesign.murashkina.dto.response.EmployeeResponse;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.services.EmployeeService;
import com.digitaldesign.murashkina.services.exceptions.employee.*;
import com.digitaldesign.murashkina.services.mapping.EmployeeMapper;
import com.digitaldesign.murashkina.services.specifications.EmployeeSpecification;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.digitaldesign.murashkina.app.services.unit.TestEntitiesCreator.createTestEmployee;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {
    @Spy
    EmployeeRepository employeeRepo;
    @Mock
    EmployeeMapper employeeMapper;
    @Spy
    Authentication authentication;
    @Spy
    SecurityContext securityContext;
    @Spy
    EmployeeSpecification employeeSpecification;
    @InjectMocks
    EmployeeService employeeService;
    @Spy
    PasswordEncoder passwordEncoder;

    @Test
    public void createEmployeeNotExist() {
        Employee employee = createTestEmployee("test");
        Employee dbemployee = createTestEmployee("dbempl");
        UUID employeeId = dbemployee.getId();
        EmployeeRequest employeeRequest = createTestEmployeeRequest("test");
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        when(employeeMapper.toEntity(employeeRequest)).thenReturn(employee);
        when(passwordEncoder.encode(any())).thenReturn("abcde");
        when(employeeRepo.save(employee)).thenReturn(dbemployee);
        when(employeeMapper.toDto(dbemployee)).thenReturn(employeeResponse);
        EmployeeResponse actualEmployee = employeeService.create(employeeRequest);
        verify(employeeRepo, times(1)).save(employee);
        Assertions.assertEquals(employeeId, actualEmployee.getId());
        Assertions.assertNotNull(actualEmployee.getAccount());
        Assertions.assertNotNull(actualEmployee.getLastName());
        Assertions.assertNotNull(actualEmployee.getFirstName());
    }

    @Test
    public void create_EmployeeIsNullTest() {
        Assertions.assertThrows(EmployeeIsNullException.class, () -> employeeService.create(EmployeeRequest.builder().build()));
        verify(employeeRepo, times(0)).save(any(Employee.class));
    }

    @Test
    public void create_AccountAlreadyExistTest() {
        EmployeeRequest request = createTestEmployeeRequest("test");
        when(employeeRepo.existsEmployeeByAccount(any())).thenReturn(true);
        Assertions.assertThrows(AccountAlreadyExistException.class, () -> employeeService.create(request));
        verify(employeeRepo, times(0)).save(any(Employee.class));
    }

    @Test
    public void upadate() {
        Employee employee = createTestEmployee("test");
        final UUID employeeId = employee.getId();
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        UpdateEmployeeRequest updateEmployeeRequest = createTestUpdateEmployeeRequest("test");
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEntity(updateEmployeeRequest)).thenReturn(employee);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.findByAccount(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeService.getEmployee(employeeId)).thenReturn(employeeResponse);
        when(employeeRepo.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        EmployeeResponse actualEmployee = employeeService.update(updateEmployeeRequest, employeeId);
        Assertions.assertEquals(employeeId, actualEmployee.getId());
        Assertions.assertNotNull(actualEmployee.getAccount());
        Assertions.assertNotNull(actualEmployee.getLastName());
        Assertions.assertNotNull(actualEmployee.getFirstName());
    }


    @Test
    public void update_AccountAlreadyExist() {
        Employee employee = createTestEmployee("test");
        employee.setRole(ERole.ROLE_ADMIN);
        UUID employeeId = employee.getId();
        UpdateEmployeeRequest request = createTestUpdateEmployeeRequest("test");
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test2", employeeId);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.findByAccount(employee.getAccount())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsEmployeeByAccount(any())).thenReturn(true);

        Assertions.assertThrows(AccountAlreadyExistException.class, () -> employeeService.update(request, employeeId));
        verify(employeeRepo, times(0)).save(any(Employee.class));
    }

    @Test
    public void updateEmployee_EmployeeNotFound() {
        Employee employee = createTestEmployee("test");
        employee.setRole(ERole.ROLE_ADMIN);
        UUID employeeId = employee.getId();
        UpdateEmployeeRequest request = createTestUpdateEmployeeRequest("test");
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.existsById(any())).thenReturn(false);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.findByAccount(employee.getAccount())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsEmployeeByAccount(any())).thenReturn(false);

        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.update(request, employeeId));
        verify(employeeRepo, times(0)).save(any(Employee.class));
    }

    @Test
    public void updateEmployee_EmployeeIsDeleted() {
        Employee employee = createTestEmployee("test");
        employee.setRole(ERole.ROLE_ADMIN);
        employee.setStatus(EStatus.BLOCKED);
        UUID employeeId = employee.getId();
        UpdateEmployeeRequest request = createTestUpdateEmployeeRequest("test");
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.findByAccount(employee.getAccount())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsEmployeeByAccount(any())).thenReturn(false);

        Assertions.assertThrows(EmployeeDeletedException.class, () -> employeeService.update(request, employeeId));
        verify(employeeRepo, times(0)).save(any(Employee.class));
    }

    @Test
    public void updateEmployee_acessDenied() {
        Employee employee = createTestEmployee("test");
        employee.setRole(ERole.ROLE_USER);
        UUID employeeId = employee.getId();
        UpdateEmployeeRequest request = createTestUpdateEmployeeRequest("test");
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test2", employeeId);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        when(employeeRepo.findById(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.findByAccount(employee.getAccount())).thenReturn(Optional.of(employee));

        Assertions.assertThrows(AccessToResourceDeniedException.class, () -> employeeService.update(request, employeeId));
        verify(employeeRepo, times(0)).save(any(Employee.class));
    }


    @Test
    public void updatePassword() {
        Employee employee = createTestEmployee("test");
        final UUID employeeId = employee.getId();
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
                .password("newpassword123")
                .confirmPassword("newpassword123").build();
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(passwordEncoder.encode(updatePasswordRequest.getPassword())).thenReturn("encodepassword");
        when(employeeRepo.findByAccount(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeService.getEmployee(employeeId)).thenReturn(employeeResponse);
        when(employeeRepo.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        EmployeeResponse actualEmployee = employeeService.updatePassword(updatePasswordRequest, employeeId);
        Assertions.assertEquals(employeeId, actualEmployee.getId());
        Assertions.assertNotNull(actualEmployee.getAccount());
        Assertions.assertNotNull(actualEmployee.getLastName());
        Assertions.assertNotNull(actualEmployee.getFirstName());
    }

    @Test
    public void updatePassword_employeeNotFound() {
        Employee employee = createTestEmployee("test");
        final UUID employeeId = employee.getId();
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
                .password("newpassword123")
                .confirmPassword("newpassword123").build();
        when(employeeRepo.existsById(any())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.updatePassword(updatePasswordRequest, employeeId));

    }

    @Test
    public void updatePassword_accessDenied() {
        Employee employee = createTestEmployee("test");
        employee.setRole(ERole.ROLE_USER);
        final UUID employeeId = employee.getId();
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test2", employeeId);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
                .password("newpassword123")
                .confirmPassword("newpassword123").build();

        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.findByAccount(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        Assertions.assertThrows(AccessToResourceDeniedException.class, () -> employeeService.updatePassword(updatePasswordRequest, employeeId));
    }

    @Test
    public void updatePassword_employeeDeleted() {
        Employee employee = createTestEmployee("test");
        employee.setStatus(EStatus.BLOCKED);
        final UUID employeeId = employee.getId();
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
                .password("newpassword123")
                .confirmPassword("newpassword123").build();

        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.findByAccount(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        Assertions.assertThrows(EmployeeDeletedException.class, () -> employeeService.updatePassword(updatePasswordRequest, employeeId));

    }

    @Test
    public void updatePassword_passwordMismatch() {
        Employee employee = createTestEmployee("test");
        final UUID employeeId = employee.getId();
        EmployeeResponse employeeResponse = createTestEmployeeResponse("test", employeeId);
        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
                .password("newpassword123")
                .confirmPassword("pass1123").build();
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(employee.getAccount());
        when(employeeRepo.findByAccount(any())).thenReturn(Optional.of(employee));
        when(employeeRepo.existsById(any())).thenReturn(true);
        when(employeeMapper.toDto(employee)).thenReturn(employeeResponse);
        Assertions.assertThrows(PasswordsMismatchException.class, () -> employeeService.updatePassword(updatePasswordRequest, employeeId));
    }

    @Test
    public void search() {
        Employee employee = createTestEmployee("user1");
        Employee employee2 = createTestEmployee("euser2");
        employee2.setFirstName("Testtest");
        employee2.setLastName("Testtest");
        employee2.setPosition("tester2");
        SearchEmployeeRequest searchEmployeeRequest = SearchEmployeeRequest.builder()
                .firstName(employee.getFirstName())
                .build();
        EmployeeResponse response = EmployeeResponse.builder()
                .id(employee.getId())
                .account(employee.getAccount())
                .lastName("Test")
                .firstName("Test")
                .status(EStatus.ACTIVE)
                .position("tester")
                .build();
        EmployeeResponse response2 = EmployeeResponse.builder()
                .id(employee2.getId())
                .account(employee2.getAccount())
                .lastName("Testtest")
                .firstName("Testtest")
                .status(EStatus.ACTIVE)
                .position("tester2")
                .build();
        when(employeeRepo.findAll(any(Specification.class))).thenReturn(List.of(employee, employee2));
        when(employeeMapper.toDto(employee)).thenReturn(response);
        when(employeeMapper.toDto(employee2)).thenReturn(response2);
        List<EmployeeResponse> employeeResponses = employeeService.search(searchEmployeeRequest);
        Assertions.assertEquals(employee.getId(), employeeResponses.get(0).getId());
        Assertions.assertEquals(employee.getAccount(), employeeResponses.get(0).getAccount());
        Assertions.assertEquals(employee.getEmail(), employeeResponses.get(0).getEmail());
        Assertions.assertEquals(employee.getFirstName(), employeeResponses.get(0).getFirstName());
        Assertions.assertEquals(employee.getLastName(), employeeResponses.get(0).getLastName());
        Assertions.assertEquals(employee.getStatus(), employeeResponses.get(0).getStatus());
        Assertions.assertEquals(employee.getPosition(), employeeResponses.get(0).getPosition());
        Assertions.assertEquals(employee2.getId(), employeeResponses.get(1).getId());
        Assertions.assertEquals(employee2.getAccount(), employeeResponses.get(1).getAccount());
        Assertions.assertEquals(employee2.getEmail(), employeeResponses.get(1).getEmail());
        Assertions.assertEquals(employee2.getFirstName(), employeeResponses.get(1).getFirstName());
        Assertions.assertEquals(employee2.getLastName(), employeeResponses.get(1).getLastName());
        Assertions.assertEquals(employee2.getStatus(), employeeResponses.get(1).getStatus());
        Assertions.assertEquals(employee2.getPosition(), employeeResponses.get(1).getPosition());

    }

    @Test
    public void getEmployee() {
        Employee employee = createTestEmployee("executor");
        EmployeeResponse response = createTestEmployeeResponse("executor", employee.getId());
        when(employeeRepo.existsById(employee.getId())).thenReturn(true);
        when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(response);
        EmployeeResponse acctualemployee = employeeService.getEmployee(employee.getId());
        verify(employeeRepo, times(1)).findById(any(UUID.class));
        Assertions.assertEquals(employee.getId(), acctualemployee.getId());
        Assertions.assertEquals(employee.getAccount(), acctualemployee.getAccount());
        Assertions.assertEquals(employee.getLastName(), acctualemployee.getLastName());
        Assertions.assertEquals(employee.getFirstName(), acctualemployee.getFirstName());
        Assertions.assertEquals(employee.getStatus(), acctualemployee.getStatus());
        Assertions.assertEquals(employee.getPosition(), acctualemployee.getPosition());
    }

    @Test
    public void deleteEmployee() {
        Employee employee = createTestEmployee("executor");
        EmployeeResponse response = createTestEmployeeResponse("executor", employee.getId());
        when(employeeRepo.existsById(employee.getId())).thenReturn(true);
        when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(response);
        EmployeeResponse acctualemployee = employeeService.delete(employee.getId());
        Assertions.assertEquals(employee.getId(), acctualemployee.getId());
        Assertions.assertEquals(employee.getAccount(), acctualemployee.getAccount());
        Assertions.assertEquals(employee.getLastName(), acctualemployee.getLastName());
        Assertions.assertEquals(employee.getFirstName(), acctualemployee.getFirstName());
        Assertions.assertEquals(employee.getStatus(), acctualemployee.getStatus());
        Assertions.assertEquals(employee.getPosition(), acctualemployee.getPosition());

    }

    @Test
    public void deleteEmployee_employeeNotFound() {
        Employee employee = createTestEmployee("executor");
        when(employeeRepo.existsById(employee.getId())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete(employee.getId()));


    }

    @Test
    public void getEmployee_EmployeeNotFound() {
        Employee employee = createTestEmployee("executor");
        EmployeeResponse response = createTestEmployeeResponse("test", employee.getId());
        when(employeeRepo.existsById(employee.getId())).thenReturn(false);
        when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(response);
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(employee.getId()));
    }

    private EmployeeRequest createTestEmployeeRequest(String account) {
        return EmployeeRequest.builder()
                .account(account)
                .lastName("Test")
                .firstName("Test")
                .password("password12")
                .position("tester")
                .build();
    }

    private UpdateEmployeeRequest createTestUpdateEmployeeRequest(String account) {
        UpdateEmployeeRequest employeeRequest = UpdateEmployeeRequest.builder()
                .account("test")
                .lastName("Test")
                .firstName("Test")
                .position("tester")
                .build();
        return employeeRequest;
    }

    private EmployeeResponse createTestEmployeeResponse(String account, UUID employeeId) {
        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .id(employeeId)
                .account(account)
                .lastName("Test")
                .firstName("Test")
                .status(EStatus.ACTIVE)
                .position("tester")
                .build();
        return employeeResponse;
    }


}
