package com.digitaldesign.murashkina.services.security;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = repository.findByAccount(username);
        return employee.map(EmployeeUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
