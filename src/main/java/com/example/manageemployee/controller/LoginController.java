package com.example.manageemployee.controller;

import com.example.manageemployee.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @ModelAttribute("userform")
    public UserDto userDto() {
        return new UserDto();
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
