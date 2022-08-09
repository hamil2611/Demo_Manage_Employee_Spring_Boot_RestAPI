package com.example.manageemployee.repository;

import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(Optional<Role> role);
    List<User> findAllByCodecheckin(Integer CodeCheckin);
    List<User> findAllByUsername(String username);
    User findByUsername(String username);
    User findByEmail(String email);
    @Query(value = "select * from user where user.fullname like %:name%", nativeQuery = true)
    List<User> findUserByFullname(String name);
    @Query("SELECT u.codecheckin FROM User as u ")
    List<Integer> findAllCodecheckin();
    @Query(value = "select * from user,role where user.role_id=role.id and role.name='ROLE_EMPLOYEE'",nativeQuery = true)
    List<User> findAllEmployee();
    @Query("select new com.example.manageemployee.model.entity.User(u.fullname) from User as u")
    List<User> getUsers();
}
