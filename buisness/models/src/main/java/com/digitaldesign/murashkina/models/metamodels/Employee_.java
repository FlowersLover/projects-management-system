package com.digitaldesign.murashkina.models.metamodels;

import com.digitaldesign.murashkina.models.employee.Employee;


import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {
    public static volatile SingularAttribute<Employee, String> firstName;
    public static volatile SingularAttribute<Employee, String> lastName;
    public static volatile SingularAttribute<Employee, String> middleName;
    public static volatile SingularAttribute<Employee, String> account;
    public static volatile SingularAttribute<Employee, String> email;
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String MIDDLENAME = "middleName";
    public static final String ACCOUNT = "account";
    public static final String EMAIL = "email";


}