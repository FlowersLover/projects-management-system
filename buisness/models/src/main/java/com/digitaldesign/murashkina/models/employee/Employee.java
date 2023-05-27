package com.digitaldesign.murashkina.models.employee;


import com.digitaldesign.murashkina.dto.enums.EStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
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

    @Size(min = 3, max = 50)
    private String position;

    @Size(min = 3, max = 50)
    private String account;

    @Column(name = "lastname")
    @NonNull
    @Size(min = 3, max = 50)
    private String lastName;

    @Column(name = "firstname")
    @NonNull
    @Size(min = 3, max = 50)
    private String firstName;

    @Column(name = "middlename")
    @Size(min = 3, max = 50)
    private String middleName;

    @Email
    @Size(max = 150)
    private String email;

    @NonNull
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @NonNull
    @Size(min = 8, max = 250)
    private String password;

}
