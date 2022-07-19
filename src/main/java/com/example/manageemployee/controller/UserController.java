package com.example.manageemployee.controller;

import com.example.manageemployee.dao.userDAO.UserDAOImpl;
import com.example.manageemployee.entity.Role;
import com.example.manageemployee.entity.User;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDAOImpl userDAOImpl;

    @Autowired
    RoleRepository roleRepository;

    @ModelAttribute("listrole")
    public  List<Role> Role(){
        List<Role> listrole = roleRepository.findAll();
        return listrole;
    }
    @GetMapping("/formnewuser")
    public String formNewUser(){
        return "formnewuser";
    }

    @PostMapping ("/newUser")
    public  String newUser(@ModelAttribute("userform") User u){
        System.out.println(u.getFullname());
        System.out.println(u.getDateofbirth());

        Date date = new Date();
        int codecheckin = ThreadLocalRandom.current().nextInt(1000,10000);
        u.setCodecheckin(codecheckin);
        u.setDatecreated(date);
        //userDAOImpl.AddUser(user);
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");
        u.setRole(role);

        userDAOImpl.newUser(u);
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

