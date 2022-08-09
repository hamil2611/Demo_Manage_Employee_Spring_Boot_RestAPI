package com.example.manageemployee.service.userDAOService;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.OnLeaveDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.User;

import java.util.List;

public interface UserDAOService {
    public UserDto getUser(int id);
    public boolean addUser(UserDto userDto);
    public boolean updateUser(UserDto userDto);
    public List<UserDto> findAllEmployee();
    public boolean deleteUser(int id);
    public UserDto getCheckinUser(int id);
    public UserDto Checkin(CheckinDto CheckinDto);
    public List<UserDto> findUserByName(String name);
    public boolean SendRequestOnLeave(OnLeaveDto onLeaveDto);
    public int getCodecheckinByUsername();
    public boolean joinPorject(int userid,int projectid);
}
