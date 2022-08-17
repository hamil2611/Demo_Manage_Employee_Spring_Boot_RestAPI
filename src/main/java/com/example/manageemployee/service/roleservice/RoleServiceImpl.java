package com.example.manageemployee.service.roleservice;

import com.example.manageemployee.model.dto.RoleDto;
import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<RoleDto> findAll() {
        return null;
    }
    @Override
    public List<User> getUsers() {
        Optional<Role> roleOptional = roleRepository.findById(1);
        Role role = this.modelMapper.map(roleOptional,Role.class);
        return null;
    }
}
