package com.digitaldesign.murashkina.services.datastorage;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.models.employee.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataStorageImpl implements DataStorage {
    static String fileName = "employees.out";
    static File file = new File(fileName);

    public static void main(String[] args) {

        DataStorageImpl dataStorage = new DataStorageImpl();
        Employee employee = Employee.builder().id(UUID.randomUUID())
                .firstName("firstname")
                .lastName("lastname")
                .middleName("middlename")
                .account("emp1")
                .email("empl@mail.com")
                .status(EStatus.ACTIVE)
                .position("position1")
                .password("qwerty123")
                .build();
        Employee employee2 = Employee.builder().id(UUID.randomUUID())
                .firstName("firstname2")
                .lastName("lastname2")
                .middleName("middlename2")
                .account("emp2")
                .email("empl2@mail.com")
                .status(EStatus.ACTIVE)
                .position("position2")
                .password("qwerty123")
                .build();
        Employee employee3 = Employee.builder().id(UUID.randomUUID())
                .firstName("firstname3")
                .lastName("lastname3")
                .middleName("middlename3")
                .account("emp3")
                .email("empl3@mail.com")
                .status(EStatus.ACTIVE)
                .position("position3")
                .password("qwerty123")
                .build();

        dataStorage.create(employee);

        dataStorage.create(employee2);
        List<Employee> list = dataStorage.getAll();
        for (Employee emp : list) {
            System.out.println(emp.toString());
        }
        System.out.println("---------------------");
        dataStorage.update(employee2,employee3);
        dataStorage.deleteById(employee.getId().toString());
        List<Employee> list2 = dataStorage.getAll();
        for (Employee emp : list2) {
            System.out.println(emp.toString());
        }
    }

    @Override
    public Employee create(Employee employee) {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            if (file.length() < 1) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(employee);
                return employee;
            } else {
                AppendingObjectOutputStream mos = new AppendingObjectOutputStream(fos);
                mos.writeObject(employee);
                return employee;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (fis.available() > 0) {
                Employee p = (Employee) ois.readObject();
                employeeList.add(p);
            }
            return employeeList;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getById(String id) {
        List<Employee> employees = getAll();
        return employees.stream().filter(emp -> emp.getId().toString().equals(id)).findFirst().get();
    }

    @Override
    public Employee deleteById(String id) {
        List<Employee> employees = getAll();
        Employee employeToDelete = employees.stream().filter(employee ->
                (employee.getId().toString().equals(id))).findFirst().get();
        employeToDelete.setStatus(EStatus.BLOCKED);
        List<Employee> employeesFiltered = new ArrayList<>(employees.stream().filter(emp -> !emp.getId().toString().equals(id)).toList());
        employeesFiltered.add(employeToDelete);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Employee employee : employeesFiltered
            ) {
                oos.writeObject(employee);
            }
            return employeToDelete;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee update(Employee oldEmployee, Employee newEmployee){
        List<Employee> employees = getAll();
        List<Employee> employeesFiltered = employees.stream().filter(emp -> !emp.getId().equals(oldEmployee.getId())).collect(Collectors.toList());
        employeesFiltered.add(newEmployee);
        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Employee employee : employeesFiltered
            ) {

                oos.writeObject(employee);
            }
            return newEmployee;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    public void writeStreamHeader() {
    }
}