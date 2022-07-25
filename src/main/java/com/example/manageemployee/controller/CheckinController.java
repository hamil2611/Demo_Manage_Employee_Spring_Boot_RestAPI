package com.example.manageemployee.controller;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.service.mailService.MailController;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class CheckinController {
    @Autowired
    CheckinRepository checkinRepository;
    @Autowired
    UserDAOServiceImpl userDAOServiceImpl;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailController mailController;
    @Autowired
    ModelMapper modelMapper;
    @ModelAttribute("checkinform")
    public CheckinDto checkin(){
        return new CheckinDto();
    }
    @GetMapping("/checkin")
    public String employeeCheckin(){

        return "checkinform";
    }
    @PostMapping("/checkin")
    public String employeeCheckin(@ModelAttribute("checkinform") CheckinDto checkinDto, Model model){
        if(checkinDto.getCodecheckin()==0){
            return "checkinform";
        }else{
            UserDto userDto = userDAOServiceImpl.Checkin(checkinDto);
            if(userDto==null){
                model.addAttribute("msgreport","CODECHECKIN DOES NOT EXIST");
                return "checkinform";
            }
            else {
                model.addAttribute("msgreport","CHECKIN SUCCESSFULLY!");
                model.addAttribute("msghello", "HELLO!   " + userDto.getFullname());
                return "checkinform";
            }
        }
    }
    @GetMapping("/viewcheckin")
    public String ViewCheckin(@RequestParam int id, Model model){
        List<Checkin> listcheckin = checkinRepository.findAllByCodecheckin(id);
        model.addAttribute("listcheckin",listcheckin);
        return "viewcheckin";
    }
}
