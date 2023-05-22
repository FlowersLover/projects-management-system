package com.digitaldesign.murashkina.dao;

import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeFilter;
import com.digitaldesign.murashkina.models.employee.Employee;

import java.util.List;

public class Main {
    public static EmployeeDao employeeDao = new EmployeeDaoJdbc(
            "jdbc:postgresql://localhost:5432/test",
            "postgres",
            "postgres");

    public static void main(String[] args) {
        //Создать сотрудника
        /*Employee employee = new Employee();
        employee.setFirstName("Петр");
        employee.setLastName("Петров");
        employee.setMiddleName("Петрович");
        employee.setAccount("petrov11");
        employee.setEmail("petrov@mail.com");
        employee.setPassword("qwerty123");
        employee.setPosition("engineer");
        //boolean createEmployeeResult = employeeDao.create(employee);
        //System.out.println(createEmployeeResult);*/

        //получить карточку сотрудника
        Employee employee = employeeDao.read("petrov11");
        //System.out.println(employee.toString());

        //Изменить данные сотрудника
        employee.setFirstName("Петр");
        Boolean res = employeeDao.update(employee);
        //System.out.println(employeeDao.read("petrov11").toString());

        //Удалить сотрудника
        //employeeDao.delete(employee.getId());

        //Поиск
        SearchEmployeeFilter searchEmployeeFilter = new SearchEmployeeFilter();
        //searchEmployeeFilter.setFirstName("Kacey");
        //searchEmployeeFilter.setEmail("mlemary2@skype.com");
        //searchEmployeeFilter.setRole("DEVELOPER");
        searchEmployeeFilter.setRole("TESTER");
        List<Employee> employeeList = employeeDao.search(searchEmployeeFilter);
        for (Employee e : employeeList
        ) {
            System.out.println(e.toString());
        }
    }
}
