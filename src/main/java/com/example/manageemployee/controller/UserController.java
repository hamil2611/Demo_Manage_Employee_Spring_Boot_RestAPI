package com.example.manageemployee.controller;

import com.example.manageemployee.dao.userDAO.UserDAOImpl;
import com.example.manageemployee.entity.Role;
import com.example.manageemployee.entity.User;
import com.example.manageemployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDAOImpl userDAOImpl;

    @GetMapping("/formnewuser")
    public String formNewUser(){
        return "formnewuser";
    }

    @PostMapping ("/newUser")
    public  String newUser(@ModelAttribute("userform") User u){
        System.out.println(u.getFullname());
        Date date = new Date();

        User user = new User();
        user.setFullname("Dinh An");
        user.setDateofbirth("26/11/2000");
        user.setAddress("Ha Noi");
        user.setUsername("dinhan");
        user.setPassword("dinhan");
        user.setEmail("dinhan@gmail.com");
        int codecheckin = ThreadLocalRandom.current().nextInt(1000,10000);
        user.setCodecheckin(codecheckin);
        user.setDatecreated(date);
        //userDAOImpl.AddUser(user);

        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");
        user.setRole(role);

        userDAOImpl.newUser(user);
        System.out.println("***End!***");
        return "newuser";
    }

    @GetMapping("/editUser")
    public  String editUser(){
        User user = new User();
        user.setId(52);
        user.setFullname("Dinh An");
        user.setUsername("dinhan");
        user.setPassword("dinhan");
        user.setEmail("dinhan@gmail.com");
        //userDAOImpl.AddUser(user);

        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");
        user.setRole(role);

        userDAOImpl.newUser(user);
        System.out.println("***End!***");
        return "newuser";
    }

    @ModelAttribute("userform")
    public User userform() {
        return new User();
    }
}

