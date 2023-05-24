package com.digitaldesign.murashkina.repositories.dao;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeFilter;
import com.digitaldesign.murashkina.models.employee.Employee;

import java.sql.*;
import java.util.*;

public class EmployeeDaoJdbc implements EmployeeDao {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public EmployeeDaoJdbc(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public Employee create(Employee e) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into employee(firstname, lastname, middlename, position, account, email, password,status) values(?, ?, ?, ?, ?, ?, ?,?)");
            preparedStatement.setString(1, e.getFirstName());
            preparedStatement.setString(2, e.getLastName());
            preparedStatement.setString(3, e.getMiddleName());
            preparedStatement.setString(4, e.getPosition());
            preparedStatement.setString(5, e.getAccount());
            preparedStatement.setString(6, e.getEmail());
            preparedStatement.setString(7, e.getPassword());
            preparedStatement.setString(8, "ACTIVE");
            preparedStatement.executeUpdate();
            return read(e.getAccount());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Employee read(String account) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from employee where account = ?");
            preparedStatement.setString(1, account);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            Employee e = Employee.builder()
                    .id(result.getObject("id", UUID.class))
                    .firstName(result.getString("firstname"))
                    .lastName(result.getString("lastname"))
                    .middleName(result.getString("middlename"))
                    .email(result.getString("email"))
                    .position(result.getString("position"))
                    .status(EStatus.valueOf(result.getString("status")))
                    .password(result.getString("password"))
                    .account(result.getString("account"))
                    .build();
            return e;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Employee read(UUID id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from employee where id = ?");
            preparedStatement.setObject(1, id);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            Employee e = Employee.builder()
                    .id(result.getObject("id", UUID.class))
                    .firstName(result.getString("firstname"))
                    .lastName(result.getString("lastname"))
                    .middleName(result.getString("middlename"))
                    .email(result.getString("email"))
                    .position(result.getString("position"))
                    .status(EStatus.valueOf(result.getString("status")))
                    .password(result.getString("password"))
                    .account(result.getString("account"))
                    .build();
            return e;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Employee update(Employee e) {
        try (Connection connection = getConnection()) {
            if (read(e.getId()).getStatus().name().equals("BLOCKED")) {
                return null;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update  employee set firstname = ?, lastname = ? ,middlename = ? ,position = ?, account = ?, email = ?, password = ?, status = ?  where id = ?");
            preparedStatement.setString(1, e.getFirstName());
            preparedStatement.setString(2, e.getLastName());
            preparedStatement.setString(3, e.getMiddleName());
            preparedStatement.setString(4, e.getPosition());
            preparedStatement.setString(5, e.getAccount());
            preparedStatement.setString(6, e.getEmail());
            preparedStatement.setString(7, e.getPassword());
            preparedStatement.setString(8, e.getStatus().name());
            preparedStatement.setObject(9, e.getId());
            preparedStatement.executeUpdate();
            return read(e.getId());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Employee delete(UUID id) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update  employee set status = 'BLOCKED' where id = ?");
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            return read(id);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Employee> search(SearchEmployeeFilter sef) {
        String searchQuery = "select * from employee e JOIN team t on  t.member = e.id ";
        Map<Integer, Object> parameterMap = new HashMap<>();
        int paramIndex = 1;
        if (sef.getFirstName() != null) {
            searchQuery += "and e.firstname = ? ";
            parameterMap.put(paramIndex++, sef.getFirstName());
        }
        if (sef.getLastName() != null) {
            searchQuery += "and e.lastname = ? ";
            parameterMap.put(paramIndex++, sef.getLastName());
        }
        if (sef.getEmail() != null) {
            searchQuery += "and e.email = ? ";
            parameterMap.put(paramIndex++, sef.getEmail());
        }

        if (sef.getMiddleName() != null) {
            searchQuery += "and e.middleName = ? ";
        } else {
            searchQuery += "or e.middleName = ? ";
        }
        parameterMap.put(paramIndex++, sef.getMiddleName());
        if (sef.getAccount() != null) {
            searchQuery += "and e.account = ? ";
        } else {
            searchQuery += "or e.account = ? ";
        }
        parameterMap.put(paramIndex++, sef.getAccount());

        if (sef.getMember() != null) {
            searchQuery += "and t.member = ? ";
            parameterMap.put(paramIndex++, sef.getMember());
        }
        if (sef.getRole() != null) {
            searchQuery += "and t.role = ? ";
            parameterMap.put(paramIndex++, sef.getRole());
        }
        if (sef.getProject() != null) {
            searchQuery += "and t.project = ? ";
            parameterMap.put(paramIndex++, sef.getProject());
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(searchQuery);
            for (Map.Entry<Integer, Object> entry : parameterMap.entrySet()) {
                Integer key = entry.getKey();
                Object value = entry.getValue();
                ps.setObject(key, value);
            }
            ResultSet rs = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                Employee e = Employee.builder().build();
                if (EStatus.valueOf(rs.getString("status")).name().equals("BLOCKED")) {
                    continue;
                }
                e.setId(rs.getObject("id", UUID.class));
                e.setFirstName(rs.getString("firstname"));
                e.setLastName(rs.getString("lastname"));
                e.setMiddleName(rs.getString("middlename"));
                e.setPosition(rs.getString("position"));
                e.setStatus(EStatus.valueOf(rs.getString("status")));
                e.setPassword(rs.getString("password"));
                e.setEmail(rs.getString("email"));
                e.setAccount(rs.getString("account"));
                employees.add(e);
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
