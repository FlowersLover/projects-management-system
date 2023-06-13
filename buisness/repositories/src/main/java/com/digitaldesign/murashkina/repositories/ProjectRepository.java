package com.digitaldesign.murashkina.repositories;


import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.models.project.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Project p set p.projectStatus = ?1 where p.projectId = ?2")
    void setProjectStatusById(ProjStatus status, UUID id);
}
