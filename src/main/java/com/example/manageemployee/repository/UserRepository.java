package com.example.manageemployee.repository;

import com.example.manageemployee.entity.Role;
import com.example.manageemployee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(Optional<Role> role);
    List<User> findAllByCodecheckin(Integer CodeCheckin);
    List<User> findAllByUsername(String username);
}
