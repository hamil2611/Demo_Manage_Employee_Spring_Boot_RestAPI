package com.example.manageemployee.controller;

import com.example.manageemployee.model.dto.RoleDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOServiceImpl;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserDAOServiceImpl userDAOServiceImpl;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CheckinDAOServiceImpl checkinDAOServiceImpl;
    //ModelAttribute
    @ModelAttribute("userDto")
    public UserDto userDto() {
        return new UserDto();
    }
    @ModelAttribute("roledto")
    public RoleDto roleDto() {
        return new RoleDto();
    }

    //HTTP Method
    @GetMapping("/viewemployee")
    public List<UserDto> viewEmployee(){
        List<UserDto> listUserDto = userDAOServiceImpl.findAllEmployee();
        listUserDto.forEach(i-> System.out.println(i.getFullname()));
        return listUserDto;
    }
    @GetMapping("/viewemployee/detail")
    public UserDto viewEmployee(@RequestParam int id){
        UserDto userDto = userDAOServiceImpl.getUser(id);
        return userDto;
    }
    @PostMapping("/newuser")
    public  String newUser(@RequestBody UserDto udto) throws ParseException {
        if(userDAOServiceImpl.addUser(udto)){
            return "Add user successfully!";
        }
        else {
            return "Add user fail!";
        }
    }
    @PutMapping("/updateuser")
    public  String editUser(@RequestBody UserDto userDto){
        if(userDAOServiceImpl.updateUser(userDto))
            return "Update Successfully!";
        else {
            return "Update Fail!";
        }
    }
    @DeleteMapping("/deleteuser")
    public String deleteUser(@RequestParam int id, Model model){
        if(userDAOServiceImpl.deleteUser(id)){
            return "Delete User Successfully!";
        }
        else{
            return "Delete Fail!";
        }
    }
    @GetMapping("/statistics/reportcheckin")
    public List<ReportCheckin> showReportcheckin(){
        try {
            return checkinDAOServiceImpl.StatisticsReportCheckin();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}