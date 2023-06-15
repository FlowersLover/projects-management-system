package com.digitaldesign.murashkina.models.employee;


import com.digitaldesign.murashkina.dto.enums.ERole;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "employee",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "account")
        })
@AllArgsConstructor
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "position")
    @NonNull
    private String position;

    private String account;

    @Column(name = "lastname")
    @NonNull
    private String lastName;

    @Column(name = "firstname")
    @NonNull
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    private String email;

    @NonNull
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Employee() {

    }
}
