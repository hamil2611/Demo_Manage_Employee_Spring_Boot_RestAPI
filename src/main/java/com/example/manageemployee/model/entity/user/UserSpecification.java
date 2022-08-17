package com.example.manageemployee.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecification implements Specification<User> {
    private SearchUser searchUser;
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        System.out.println("CALLED");
        if(searchUser.getOperation().equals(SearchUser.Operation.GREATER)){
            System.out.println("GREATER");
            return criteriaBuilder.greaterThan(root.get(searchUser.getProperty()),searchUser.getValue().toString());
        }
        else if(searchUser.getOperation().equals(SearchUser.Operation.LESS)){
            System.out.println("LESS");
            return criteriaBuilder.lessThan(root.get(searchUser.getProperty()),searchUser.getValue().toString());
        }
        else if(searchUser.getOperation().equals(SearchUser.Operation.LIKE)){
            System.out.println("LIKE");
            return criteriaBuilder.like(root.get(searchUser.getProperty()),"%"+searchUser.getValue().toString()+"%");
        }
        else if(searchUser.getOperation().equals(SearchUser.Operation.EQUAL)){
            System.out.println("EQUAL");
            return criteriaBuilder.equal(root.get(searchUser.getProperty()),searchUser.getValue().toString());
        }
        System.out.println("CHECK POINT!");
        return null;
    }
}
