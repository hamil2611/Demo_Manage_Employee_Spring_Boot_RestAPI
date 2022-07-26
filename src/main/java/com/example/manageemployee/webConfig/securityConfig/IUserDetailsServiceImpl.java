package com.example.manageemployee.webConfig.securityConfig;

import com.example.manageemployee.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserDetailsServiceImpl extends UserDetailsService {
    public List<User> getUser(String username);
}
