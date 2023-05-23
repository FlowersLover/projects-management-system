package com.digitaldesign.murashkina.services.datastorage;

import com.digitaldesign.murashkina.models.employee.Employee;

import java.util.List;

public interface DataStorage {
    public Employee create(Employee employee);

    public List<Employee> getAll();

    public Employee getById(String id) ;

    public Employee deleteById(String id);

    public Employee update(Employee oldEmployee, Employee newEmployee);
}
