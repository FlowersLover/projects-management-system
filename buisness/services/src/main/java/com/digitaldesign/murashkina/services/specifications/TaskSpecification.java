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
   /* public Specification<Task> tasknameEqual(String taskname) {
        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (taskname == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Task_.TASK_NAME), taskname);
            }
        };
    }

    public Specification<Task> executorEqual(UUID executor) {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Task_.EXECUTOR), executor);
            }
        };
    }

    public Specification<Task> deadlineEqual(Date deadline) {

        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (deadline == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Task_.DEADLINE), deadline);
            }
        };
    }

    public Specification<Task> createdAtEqual(Date createdAt) {
        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (createdAt == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Task_.CREATED_AT), createdAt);
            }
        };
    }

    public Specification<Task> authorEqual(UUID author) {

        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (author == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Task_.AUTHOR), author);
            }
        };
    }

    public Specification<Task> statusEqual(TaskStatus status) {

        return new Specification<Task>() {

            @Override
            public Predicate toPredicate(Root<Task> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (status == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get(Task_.STATUS), status);
            }
        };
    }*/
}
