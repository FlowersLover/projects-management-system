package com.digitaldesign.murashkina.services.datastorage;

import com.digitaldesign.murashkina.models.employee.Employee;

import java.io.IOException;

public interface DataStorage {
    public void create(Employee obj) throws IOException;

    public Object getAll() throws IOException, ClassNotFoundException;

    public Object getById(String id) throws IOException, ClassNotFoundException;

    public void deleteById(String id) throws IOException, ClassNotFoundException;

    public void update(Employee e1, Employee e2) throws IOException, ClassNotFoundException;
}
