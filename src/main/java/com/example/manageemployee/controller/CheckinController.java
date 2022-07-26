package com.example.manageemployee.controller;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.enummodel.EnumStatus;
import com.example.manageemployee.repository.ReportCheckinRepository;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOServiceImpl;
import com.example.manageemployee.service.mailService.MailService;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class CheckinController {
    @Autowired
    UserDAOServiceImpl userDAOServiceImpl;
    @Autowired
    CheckinDAOServiceImpl checkinDAOServiceImpl;
    @Autowired
    MailService mailController;
    @Autowired
    ModelMapper modelMapper;
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
