package com.example.manageemployee.service.userDAOService;

import com.example.manageemployee.dto.UserDto;

import java.util.List;

public interface UserDAOService {
    public boolean addUser(UserDto userDto);
    public boolean editUser(int id);
    public List<UserDto> findAll();
    public List<UserDto> deleteUser(int id);
    public UserDto getCheckinUser(int id);
}
