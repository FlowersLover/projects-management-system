package com.digitaldesign.murashkina.models.employee;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Builder
@Setter
@Getter
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String position;
    private String account;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "middlename")
    private String middleName;
    private String email;
    private EStatus status;
    private String password;
}
