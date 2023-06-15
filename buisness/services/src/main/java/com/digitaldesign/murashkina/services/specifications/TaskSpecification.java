package com.digitaldesign.murashkina.services.specifications;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.models.metamodels.Task_;
import com.digitaldesign.murashkina.models.task.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TaskSpecification {
    public Specification<Task> tasknameEqual(String taskname) {
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
    }
}
