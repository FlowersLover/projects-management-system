package com.digitaldesign.murashkina.services.specifications;

import com.digitaldesign.murashkina.dto.request.task.SearchTaskRequest;
import com.digitaldesign.murashkina.models.task.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskSpecification {
    public Specification<Task> getSpecification(SearchTaskRequest searchTask) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(searchTask.getTaskName())) {
                predicates.add(criteriaBuilder.equal(root.get("taskName"),
                        searchTask.getTaskName()));
            }
            if (!ObjectUtils.isEmpty(searchTask.getExecutor())) {
                predicates.add(criteriaBuilder.equal(root.get("executor"),
                        searchTask.getExecutor()));
            }
            if (!ObjectUtils.isEmpty(searchTask.getAuthor())) {
                predicates.add(criteriaBuilder.equal(root.get("author"),
                        searchTask.getAuthor()));
            }
            if (!ObjectUtils.isEmpty(searchTask.getDeadline())) {
                predicates.add(criteriaBuilder.equal(root.get("deadline"),
                        searchTask.getDeadline()));
            }
            if (!ObjectUtils.isEmpty(searchTask.getCreatedAt())) {
                predicates.add(criteriaBuilder.equal(root.get("createdAt"),
                        searchTask.getCreatedAt()));
            }
            if (!ObjectUtils.isEmpty(searchTask.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"),
                        searchTask.getStatus()));
            }
            if (CollectionUtils.isEmpty(predicates)) {
                return query.where().getRestriction();
            } else {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }
        });
    }
}
