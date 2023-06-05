package com.digitaldesign.murashkina.web.controllers;

import com.digitaldesign.murashkina.dto.request.employee.*;
import com.digitaldesign.murashkina.dto.response.EmployeeResponse;
import com.digitaldesign.murashkina.services.EmployeeService;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeDeletedException;
import com.digitaldesign.murashkina.services.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
@Tag(name = "EmployeeController", description = "Контроллер сотрудника")
public class EmployeeController {
    private EmployeeService employeeService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String getTest() {
        return "Helloo";
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getAccount(), authRequest.getPassword()));
        if (employeeService.findByAccount(authRequest.getAccount()).getStatus().name().equals("BLOCKED")) {
            throw new EmployeeDeletedException();
        }
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getAccount());
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }

    @Operation(summary = "Создание сотрудника")
    @PostMapping("/new")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @Operation(summary = "Изменение сотрудника")
    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable("employeeId") String employeeId,
                                                           @RequestBody @Valid UpdateEmployeeRequest request) {
        EmployeeResponse update = employeeService.update(request, UUID.fromString(employeeId));
        return ResponseEntity.ok(update);
    }

    @Operation(summary = "Изменение пароля сотрудника")
    @PutMapping(path = "/password/{employeeId}")
    public ResponseEntity<EmployeeResponse> updatePassword(@PathVariable("employeeId") String employeeId, @RequestBody @Valid UpdatePasswordRequest request) {
        EmployeeResponse employeeResponse = employeeService.updatePassword(request, UUID.fromString(employeeId));
        return ResponseEntity.ok(employeeResponse);
    }

    @Operation(summary = "Удаление сотрудника")
    @DeleteMapping(path = "/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> deleteEmploye(@PathVariable("employeeId") String employeeId) {
        employeeService.delete(UUID.fromString(employeeId));
        EmployeeResponse employeeResponse = employeeService.getInfo(UUID.fromString(employeeId));
        return ResponseEntity.ok().body(employeeResponse);
    }

    @Operation(summary = "Получить информацию о сотруднике")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployeeInfo(@PathVariable("employeeId") String employeeId) {
        EmployeeResponse employeeResponse = employeeService.getInfo(UUID.fromString(employeeId));
        return ResponseEntity.ok(employeeResponse);
    }

    @Operation(summary = "Поиск сотрудника")
    @GetMapping(value = "/search", params = {"account", "lastName", "firstName", "middleName", "email"})
    public ResponseEntity<List<EmployeeResponse>> searchEmployee(@RequestParam(value = "account", required = false) String account,
                                                                 @RequestParam(value = "lastName", required = false) String lastName,
                                                                 @RequestParam(value = "firstName", required = false) String firstName,
                                                                 @RequestParam(value = "middleName", required = false) String middleName,
                                                                 @RequestParam(value = "email", required = false) String email) {
        SearchEmployeeRequest request = SearchEmployeeRequest.builder().account(account).
                lastName(lastName)
                .firstName(firstName)
                .middleName(middleName)
                .email(email).build();
        List<EmployeeResponse> employeeResponse = employeeService.search(request);
        return ResponseEntity.ok(employeeResponse);
    }

}
