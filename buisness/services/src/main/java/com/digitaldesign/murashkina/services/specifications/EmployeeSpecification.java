package com.digitaldesign.murashkina.services.specifications;

import com.digitaldesign.murashkina.dto.request.employee.SearchEmployeeRequest;
import com.digitaldesign.murashkina.models.employee.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@Component
public class EmployeeSpecification {
    public Specification<Employee> getSpecification(SearchEmployeeRequest searchEmployee) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(searchEmployee.getFirstName())) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"),
                        searchEmployee.getFirstName()));
            }
            if (!ObjectUtils.isEmpty(searchEmployee.getLastName())) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"),
                        searchEmployee.getLastName()));
            }
            if (!ObjectUtils.isEmpty(searchEmployee.getMiddleName())) {
                predicates.add(criteriaBuilder.equal(root.get("middleName"),
                        searchEmployee.getMiddleName()));
            }
            if (!ObjectUtils.isEmpty(searchEmployee.getAccount())) {
                predicates.add(criteriaBuilder.equal(root.get("account"),
                        searchEmployee.getAccount()));
            }
            if (!ObjectUtils.isEmpty(searchEmployee.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"),
                        searchEmployee.getEmail()));
            }
            if (CollectionUtils.isEmpty(predicates)) {
                return query.where().getRestriction();
            } else {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }
        });
    }
}
