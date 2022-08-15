package com.example.manageemployee.controller;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.service.checkinservice.CheckinServiceImpl;
import com.example.manageemployee.service.mailservice.MailService;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckinController {
    @Autowired
    UserServiceImpl userDAOServiceImpl;
    @GetMapping("/checkin")
    public String employeeCheckin(){
        return "checkinform";
    }
    @PostMapping("/checkin")
    public String employeeCheckin(@RequestBody CheckinDto checkinDto){
        if(checkinDto.getCodecheckin()==0){
            return "CODECHECKIN DOES NOT EXIST!";
        }else{
            UserDto userDto = userDAOServiceImpl.Checkin(checkinDto);
            if(userDto==null){
                return "USER DOES NOT EXIST!";
            }
            else {
                return "HELLO" + " " + userDto.getFullname();
            }
        }
    }

}
