package com.example.manageemployee.service.userDAOService;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.UserDto;

import java.util.List;

public interface UserDAOService {
    public boolean addUser(UserDto userDto);
    public boolean editUser(int id);
    public List<UserDto> findAll();
    public List<UserDto> deleteUser(int id);
    public UserDto getCheckinUser(int id);
    public UserDto Checkin(CheckinDto CheckinDto);
}
