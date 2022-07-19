package com.example.manageemployee.service;

import com.example.manageemployee.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getUser(String username);
}
