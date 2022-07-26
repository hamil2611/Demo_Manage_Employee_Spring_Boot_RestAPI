package com.example.manageemployee.controller;

import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.service.mailService.MailService;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDAOServiceImpl userDAOServiceImpl;
    @Autowired
    MailService mailService;
    @Autowired
    ModelMapper modelMapper;
    @ModelAttribute("userform")
    public UserDto userDto() {
        return new UserDto();
    }
    @PostMapping("/register")
    public  String registerUser(@RequestBody UserDto udto) throws ParseException {
        if(userDAOServiceImpl.addUser(udto)){
            User user = this.modelMapper.map(udto,User.class);
            mailService.SendMailRegister(user);
            return "Add user successfully!";
        }
        else {
            return "Add user fail!";
        }
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
//    @PostMapping("/login")
//    public String login(@ModelAttribute("userform") UserDto udto){
//        Authentication auth =authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(udto.getUsername(), udto.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        String token = jwtProvider.createToken(auth);
//        UserDetails userDetails = (UserDetails) auth.getDetails();
//        return "login";
//    }
}
