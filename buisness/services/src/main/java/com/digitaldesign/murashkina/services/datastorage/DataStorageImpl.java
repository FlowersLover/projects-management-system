package com.digitaldesign.murashkina.services.datastorage;

import com.digitaldesign.murashkina.models.employee.EStatus;
import com.digitaldesign.murashkina.models.employee.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataStorageImpl implements DataStorage {
    static String fileName = "employees.out";
    static File file = new File(fileName);

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        DataStorageImpl dataStorage = new DataStorageImpl();
        Employee employee = new Employee(UUID.randomUUID(),
                "position",
                "emp1",
                "lastname",
                "firstName",
                "middlename",
                "email",
                EStatus.ACTIVE,
                "password");
        Employee employee2 = new Employee(UUID.randomUUID(),
                "position2",
                "emp2",
                "lastname2",
                "firstName2",
                "middlename2",
                "email2",
                EStatus.ACTIVE,
                "password2");
        Employee employee3 = new Employee(UUID.randomUUID(),
                "position3",
                "emp3",
                "lastname3",
                "firstName3",
                "middlename3",
                "email3",
                EStatus.ACTIVE,
                "password3");


        dataStorage.create(employee);
        dataStorage.create(employee2);
        List<Employee> list = dataStorage.getAll();
        for (Employee emp : list) {
            System.out.println(emp.toString());
        }
    }

    @Override
    public void create(Employee employee) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, true);
        if (file.length() < 1) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(employee);
            oos.close();
        } else {
            MyObjectOutputStream mos = new MyObjectOutputStream(fos);
            mos.writeObject(employee);
            mos.close();
        }
    }

    @Override
    public List<Employee> getAll() throws IOException, ClassNotFoundException {
        List<Employee> list = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        while (fis.available() > 0) {
            Employee p = (Employee) ois.readObject();
            list.add(p);
        }
        ois.close();
        return list;
    }

    @Override
    public Employee getById(String id) throws IOException, ClassNotFoundException {
        List<Employee> employees = getAll();
        return employees.stream().filter(emp -> emp.getId().toString().equals(id)).findFirst().get();
    }

    @Override
    public void deleteById(String id) throws IOException, ClassNotFoundException {
        List<Employee> employees = getAll();
        List<Employee> employeesFiltered = employees.stream().filter(emp -> !emp.getId().toString().equals(id)).toList();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (Employee employee : employeesFiltered
        ) {
            oos.writeObject(employee);
        }
        oos.close();
    }

    @Override
    public void update(Employee e1, Employee e2) throws IOException, ClassNotFoundException {
        List<Employee> employees = getAll();
        List<Employee> employeesFiltered = employees.stream().filter(emp -> !emp.getId().equals(e1.getId())).collect(Collectors.toList());
        employeesFiltered.add(e2);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (Employee employee : employeesFiltered
        ) {
            oos.writeObject(employee);
        }
        oos.close();
    }
}

class MyObjectOutputStream extends ObjectOutputStream {

    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    public void writeStreamHeader() throws IOException {
        return;
    }
}