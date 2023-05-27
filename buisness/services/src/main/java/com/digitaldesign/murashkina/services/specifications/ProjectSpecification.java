package com.digitaldesign.murashkina.services.specifications;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.models.metamodels.Project_;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class ProjectSpecification {

    public Specification<Project> projectNameLike(String projectName) {

        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (projectName == null) {
                    return null;
                }
                return criteriaBuilder.like(root.get(Project_.PROJECT_NAME), "%" + projectName + "%");
            }
        };
    }

    public Specification<Project> statusEquals(List<ProjStatus> statuses) {

        Specification<Project> specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        for (
                ProjStatus projectStatus : statuses) {
            specification = specification.and(new Specification<Project>() {
                @Override
                public Predicate toPredicate(Root<Project> root,
                                             CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    if (statuses == null) {
                        return null;
                    }
                    return criteriaBuilder.equal(root.get(Project_.PROJECT_STATUS), projectStatus);
                }
            });
        }
        return specification;
    }


   /* public Specification<Project> statusEquals(ProjStatus status) {

        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (status == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Project_.PROJECT_STATUS), status);
            }
        };
    }*/

    public Specification<Project> idEquals(UUID id) {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (id == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Project_.ID), id);
            }
        };
    }

}
