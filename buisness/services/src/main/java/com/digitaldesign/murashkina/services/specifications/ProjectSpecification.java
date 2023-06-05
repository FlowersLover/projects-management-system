package com.digitaldesign.murashkina.services.specifications;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.request.project.SearchProjRequest;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProjectSpecification {

    public Specification<Project> getSpecification(SearchProjRequest searchProject) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(searchProject.getProjectName())) {
                predicates.add(criteriaBuilder.equal(root.get("projectName"),
                        searchProject.getProjectName()));
            }
            if (!ObjectUtils.isEmpty(searchProject.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"),
                        searchProject.getId()));
            }
            if (!ObjectUtils.isEmpty(searchProject.getStatuses())) {
                List<ProjStatus> statuses = searchProject.getStatuses();
                List<Predicate> statusesPredicates = new ArrayList<>();
                statusesPredicates.add(criteriaBuilder.equal(root.get("projectStatus"), statuses.get(0)));
                for (int i = 1; i < statuses.size(); i++) {
                    statusesPredicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("projectStatus"), statuses.get(i)),
                            statusesPredicates.get(i - 1)));
                }
                predicates.add(statusesPredicates.get(statusesPredicates.size() - 1));
            }
            if (CollectionUtils.isEmpty(predicates)) {
                return query.where().getRestriction();
            } else {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }
        });
    }

}
