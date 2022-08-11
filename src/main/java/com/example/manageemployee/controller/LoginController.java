package com.example.manageemployee.controller;

import com.example.manageemployee.jwt.JwtResponse;
import com.example.manageemployee.jwt.JwtService;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.service.googleservice.GooglePojo;
import com.example.manageemployee.service.googleservice.GoogleUtils;
import com.example.manageemployee.service.mailservice.MailService;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.IUserDetailsServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserServiceImpl userDAOServiceImpl;
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
            mailService.SendMailFile(user);
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
    @Autowired
    GoogleUtils googleUtils;
    private static final String SECRET_KEY = "123456789";
    private static final long EXPIRE_TIME = 86400000000L;

    @Autowired
    private IUserDetailsServiceImpl userService;
    @GetMapping("/loginwithgoogle")
    public UserPrinciple loginGoogle(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
//        if (code == null || code.isEmpty()) {
//            return "redirect:/login?google=error";
//        }
        System.out.println("Code login with google:" + code);
        String accessToken = googleUtils.getToken(code);
        System.out.println("Get Token Complete!");
        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        System.out.println("Get UserInfo Complete!");
        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        System.out.println("Get buildUser Complete!");
        System.out.println("loginGoogle" + userDetail.getUsername());
        UserDto userDto = userDAOServiceImpl.getUserByEmail(googlePojo.getEmail());
        String jwt = Jwts.builder()
                .setSubject((userDto.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        UserDetails userDetails = userService.loadUserByUsername(userDto.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        UserPrinciple userPrinciple =(UserPrinciple) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userPrinciple;
    }
    @GetMapping("/logout")
    public String logout(){
        return null;
    }
}
