package com.digitaldesign.murashkina.repositories.dao;

import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeFilter;
import com.digitaldesign.murashkina.models.employee.Employee;

import java.util.List;
import java.util.UUID;

public class Main {
    public static EmployeeDao employeeDao = new EmployeeDaoJdbc(
            "jdbc:postgresql://localhost:5432/test",
            "postgres",
            "postgres");

    public static void main(String[] args) {
        //Создать сотрудника
        /*Employee employee = Employee.builder().build();
        employee.setFirstName("Петр");
        employee.setLastName("Петров");
        employee.setMiddleName("Петрович");
        employee.setAccount("petro");
        employee.setEmail("petrov@mail.com");
        employee.setPassword("qwerty123");
        employee.setPosition("engineer");
        Employee createEmployeeResult = employeeDao.create(employee);

        System.out.println(createEmployeeResult.toString());*/

        //получить карточку сотрудника
        Employee employee2 = employeeDao.read("petrov123");
        //System.out.println(employee2.toString());

        /*Employee employee3 = employeeDao.read(employee2.getId());
        System.out.println(employee3.toString());*/

        //Изменить данные сотрудника
        employee2.setFirstName("Саша");
        Employee res = employeeDao.update(employee2);
        //System.out.println(res.toString());

        //Удалить сотрудника
        /*Employee deleteEmp = employeeDao.delete(employee2.getId());
        System.out.println(deleteEmp.toString());*/

        //Поиск
        SearchEmployeeFilter searchEmployeeFilter = SearchEmployeeFilter.builder()
                //.email("mlemary2@skype.com")
                //.lastName("Duligall")
                .middleName("Lemary")
                //.account("mlemary21")
                .build();
        //searchEmployeeFilter.setFirstName("Kacey");
        //searchEmployeeFilter.setEmail("mlemary2@skype.com");
        //searchEmployeeFilter.setRole("DEVELOPER");
        List<Employee> employeeList = employeeDao.search(searchEmployeeFilter);
        for (Employee e : employeeList
        ) {
            System.out.println(e.toString());
        }
    }
}
