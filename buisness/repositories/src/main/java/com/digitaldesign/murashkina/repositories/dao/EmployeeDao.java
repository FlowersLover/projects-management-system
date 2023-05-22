package com.digitaldesign.murashkina.repositories.dao;

import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeFilter;
import com.digitaldesign.murashkina.models.employee.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeDao {
    boolean create(Employee e);
    Employee read(String account);
    Employee read(UUID id);
    boolean update(Employee e);
    boolean delete(UUID id);
    List<Employee> search(SearchEmployeeFilter searchEmployeeFilter);


}
