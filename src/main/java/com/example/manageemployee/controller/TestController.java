package com.example.manageemployee.controller;

import com.example.manageemployee.jwt.JwtResponse;
import com.example.manageemployee.jwt.JwtService;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOServiceImpl;
import com.example.manageemployee.service.roleDAOService.RoleDAOServiceImpl;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.IUserDetailsServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



class UserThread extends Thread {
    public void run() {
        for(int i=1; i <= 10; i++) {
            System.out.println("User Thread" + i);
            try {
                Thread.sleep(1000); // sleep/stop a thread for 1 second
            } catch(InterruptedException e) {
                System.out.println("An Excetion occured: " + e);
            }
        }
    }
}
@RestController
public class TestController {
    @Autowired
    RoleDAOServiceImpl roleDAOServiceImpl;
    @Autowired
    CheckinDAOServiceImpl checkinDAOServiceImpl;
    @Autowired
    UserRepository userRepository;
    //@Scheduled(fixedRateString= "20000", initialDelay = 10000)
   // @Async
    public void thread1(){
        for(int i=1; i <= 10; i++) {
            System.out.println("User Thread" + i);
            try {
                Thread.sleep(1000); // sleep/stop a thread for 1 second
            } catch(InterruptedException e) {
                System.out.println("An Excetion occured: " + e);
            }
        }
    }
    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserDetailsServiceImpl userDAOService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(), userDetails.getPassword(),userDetails.getAuthorities() ));
    }
    @PostMapping("/filtertoken")
    public boolean filterToken() {

        return false;
    }
}
