package com.example.manageemployee.service.roleservice;

import com.example.manageemployee.model.dto.RoleDto;
import com.example.manageemployee.model.entity.user.User;

import java.util.List;

public interface RoleService {
    public List<RoleDto> findAll();
    public List<User> getUsers();
}
