package com.digitaldesign.murashkina.models.metamodels;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.UUID;


public class Project_ {
    public static volatile SingularAttribute<Project, String> projectName;
    public static volatile SingularAttribute<Project, UUID> id;
    public static volatile SingularAttribute<Project, ProjStatus> projectStatus;
    public static final String PROJECT_NAME = "projectName";
    public static final String ID = "id";
    public static final String PROJECT_STATUS = "projectStatus";

}
