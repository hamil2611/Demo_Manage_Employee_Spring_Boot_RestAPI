package com.example.manageemployee.controller;

import com.example.manageemployee.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping()
public class Home {

    @GetMapping()
    public String home(){
        return "home";
    }






}
