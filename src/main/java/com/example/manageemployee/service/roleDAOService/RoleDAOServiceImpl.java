package com.example.manageemployee.service.roleDAOService;

import com.example.manageemployee.model.dto.RoleDto;
import com.example.manageemployee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleDAOServiceImpl implements  RoleDAOService{
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<RoleDto> findAll() {
        return null;
    }
}
