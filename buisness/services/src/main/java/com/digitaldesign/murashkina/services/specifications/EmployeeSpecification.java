package com.digitaldesign.murashkina.services.specifications;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.metamodels.Employee_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSpecification {
    public Specification<Employee> firstnameLike(String firstname) {
        if(firstname==null){
            return new Specification<Employee>() {
                @Override
                public Predicate toPredicate(Root<Employee> root,
                                             CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    return null;
                }
            };
        }
        return new Specification<Employee>() {

            @Override
            public Predicate toPredicate(Root<Employee> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.FIRSTNAME),  firstname );
            }
        };
    }

    public Specification<Employee> lastnameLike(String lastname) {
        if(lastname==null){
            return new Specification<Employee>() {
                @Override
                public Predicate toPredicate(Root<Employee> root,
                                             CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    return null;
                }
            };
        }
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.LASTNAME),  lastname );
            }
        };
    }

    public Specification<Employee> middlenameLike(String middlename) {

        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.MIDDLENAME), middlename );
            }
        };
    }

    public Specification<Employee> accountEquals(String account) {
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.ACCOUNT), account);
            }
        };
    }

    public Specification<Employee> emailEquals(String email) {
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(Employee_.EMAIL), email);
            }
        };
    }
}
