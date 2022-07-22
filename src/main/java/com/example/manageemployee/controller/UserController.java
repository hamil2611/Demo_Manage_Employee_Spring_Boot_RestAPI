package com.example.manageemployee.controller;

import com.example.manageemployee.dao.userDAO.UserDAOImpl;
import com.example.manageemployee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDAOImpl userDAOImpl;
    @Autowired
    RoleRepository roleRepository;


}

