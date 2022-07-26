package com.example.manageemployee.service.roleDAOService;

import com.example.manageemployee.model.dto.RoleDto;
import com.example.manageemployee.model.entity.User;

import java.util.List;

public interface RoleDAOService {
    public List<RoleDto> findAll();
    public List<User> getUsers();
}
