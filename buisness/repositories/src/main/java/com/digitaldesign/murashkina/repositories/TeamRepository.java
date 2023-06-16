package com.digitaldesign.murashkina.repositories;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, TeamId> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Team t where t.teamId.project = ?1 and t.teamId.member = ?2")
    void deleteMember(Project project, Employee member);

}
