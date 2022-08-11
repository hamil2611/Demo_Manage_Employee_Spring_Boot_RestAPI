package com.example.manageemployee.service.userservice;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.OnLeaveDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.Role;

import java.util.List;

public interface UserService {
    public UserDto getUser(int id);
    public boolean addUser(UserDto userDto);
    public boolean updateUser(UserDto userDto);
    public List<UserDto> findAllEmployee();
    public boolean deleteUser(int id);
    public UserDto getCheckinUser(int id);
    public UserDto Checkin(CheckinDto CheckinDto);
    public List<UserDto> findUserByName(String name);
    public boolean sendRequestOnLeave(OnLeaveDto onLeaveDto);
    public int getCodecheckinByUsername();
    public boolean joinPorject(int userid,int projectid);
    public List<Role> getRoles();
    public UserDto getUserByEmail(String email);

}
