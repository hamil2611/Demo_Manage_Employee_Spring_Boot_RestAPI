package com.example.manageemployee.controller;

import com.example.manageemployee.jwt.JwtResponse;
import com.example.manageemployee.jwt.JwtService;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.service.googleservice.GoogleService;
import com.example.manageemployee.service.mailservice.MailService;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;


@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserServiceImpl userDAOServiceImpl;
    @Autowired
    GoogleService googleService;
    @Autowired
    MailService mailService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JwtService jwtService;
    @ModelAttribute("userform")
    public UserDto userDto() {
        return new UserDto();
    }
    @PostMapping("/register")
    public  String registerUser(@RequestBody UserDto udto) throws ParseException, MessagingException {
        if(userDAOServiceImpl.addUser(udto)){
            User user = this.modelMapper.map(udto,User.class);
            mailService.sendMailFile(user);
            return "Add user successfully!";
        }
        else {
            return "Add user fail!";
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("testlogin");
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(), userDetails.getPassword(),userDetails.getAuthorities() ));
    }


    @GetMapping("/loginwithgoogle")
    public ResponseEntity loginGoogle(HttpServletRequest request) throws IOException {
       return ResponseEntity.ok(googleService.googleLogin(request));
    }
    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.ok("LOGOUT");
    }
}
