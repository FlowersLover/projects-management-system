package com.digitaldesign.murashkina.app.services.integretion.repos;

import com.digitaldesign.murashkina.app.services.TestConfig;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@Import(TestConfig.class)
public class EmployeeRepoIT {
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void setEmployeeStatusById() {
        Employee employee = Employee.builder()
                .firstName("name").lastName("last").account("user1123")
                .position("position").password("qwerty12345").status(EStatus.ACTIVE).build();
        Employee dbemployee = employeeRepository.save(employee);
        employeeRepository.setEmployeeStatusById(EStatus.BLOCKED, dbemployee.getId());
        Optional<Employee> employeeChangedStatus = employeeRepository.findById(dbemployee.getId());
        Assertions.assertEquals(EStatus.BLOCKED, employeeChangedStatus.get().getStatus());
        Assertions.assertEquals(dbemployee.getAccount(), employeeChangedStatus.get().getAccount());
    }

    @Test
    void existsEmployeeByAccount() {
        Employee employee = Employee.builder()
                .firstName("name").lastName("last").account("user1123")
                .position("position").password("qwerty12345").status(EStatus.ACTIVE).build();
        Employee dbemployee = employeeRepository.save(employee);
        Boolean existsed = employeeRepository.existsEmployeeByAccount(dbemployee.getAccount());
        Assertions.assertTrue(existsed);
    }

    @Test
    void findByAccount() {
        Employee employee = Employee.builder()
                .firstName("name").lastName("last").account("user1123")
                .position("position").password("qwerty12345").status(EStatus.ACTIVE).build();
        Employee dbemployee = employeeRepository.save(employee);
        Optional<Employee> employeebyAccount = employeeRepository.findByAccount(employee.getAccount());
        Assertions.assertTrue(employeebyAccount.isPresent());
        Assertions.assertEquals(dbemployee.getId(), employeebyAccount.get().getId());
        Assertions.assertEquals(dbemployee.getAccount(), employeebyAccount.get().getAccount());
    }

}

