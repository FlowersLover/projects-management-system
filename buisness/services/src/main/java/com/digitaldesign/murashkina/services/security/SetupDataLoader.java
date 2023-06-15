package com.digitaldesign.murashkina.services.security;

import com.digitaldesign.murashkina.dto.enums.ERole;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (employeeRepository.findByAccount("admin").isEmpty()) {
            Employee employee = new Employee();
            employee.setPosition("test");
            employee.setAccount("admin");
            employee.setFirstName("Test");
            employee.setLastName("Test");
            employee.setPassword(passwordEncoder.encode("admin"));
            employee.setEmail("test@test.com");
            employee.setRole(ERole.ROLE_ADMIN);
            employee.setStatus(EStatus.ACTIVE);
            employeeRepository.save(employee);
        }
    }
}
