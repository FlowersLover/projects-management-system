package com.digitaldesign.murashkina.app.services.integretion.repos;

import com.digitaldesign.murashkina.app.services.TestConfig;
import com.digitaldesign.murashkina.app.services.integretion.BaseTest;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;


@DataJpaTest
@Import(TestConfig.class)
public class ProjectRepoIT extends BaseTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void setProjectStatusById(){
        Project project = Project.builder().projectName("test")
                .description("test test")
                .projectStatus(ProjStatus.TEST).build();
        Project dbproject = projectRepository.save(project);
        projectRepository.setProjectStatusById(ProjStatus.DEVELOP,dbproject.getProjectId());
        Optional<Project> projectById = projectRepository.findById(dbproject.getProjectId());
        Assertions.assertNotNull(projectById);
        Assertions.assertEquals(ProjStatus.DEVELOP,projectById.get().getProjectStatus());
    }
}
