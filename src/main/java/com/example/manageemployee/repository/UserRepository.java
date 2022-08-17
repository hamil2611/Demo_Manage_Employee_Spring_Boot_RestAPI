package com.example.manageemployee.repository;

import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.user.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> , JpaSpecificationExecutor<User> {
    List<User> findAllByRole(Role role);
    List<User> findAllByCodecheckin(Integer CodeCheckin);
    List<User> findAllByUsername(String username);
    User findByUsername(String username);
    User findByEmail(String email);
    @Query(value = "select * from user where user.fullname like %:name%", nativeQuery = true)
    List<User> findUserByFullname(String name);
    @Query("SELECT u.codecheckin FROM User as u ")
    List<Integer> findAllCodecheckin();
    @Cacheable(cacheNames="findallemployee",condition="#id > 1")
    @Query(value = "select * from user,role where user.role_id=role.id and role.name='ROLE_EMPLOYEE'",nativeQuery = true)
    List<User> findAllEmployee();
    @Query("select new com.example.manageemployee.model.entity.user.User(u.fullname) from User as u")
    List<User> getUsers();
    @Cacheable(cacheNames="findby", condition="#id > 1")
    <T> List<T> findBy(Class<T> classType);
    @CacheEvict(cacheNames={"findby","findallemployee"}, allEntries=true)
    <T extends User> T save(T object);
    @Query("SELECT u FROM User u")
    List<User> findAllSort(Sort sort);
    @Query("SELECT u FROM User u")
    Page<User> findAllPage(Pageable pageable);

}
