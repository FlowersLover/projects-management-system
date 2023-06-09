package com.digitaldesign.murashkina.repositories;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.models.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Employee e set e.status = ?1 where e.id = ?2")
    void setEmployeeStatusById(EStatus status, UUID userId);

    Boolean existsEmployeeByAccount(String account);

    Optional<Employee> findByAccount(String username);
}
