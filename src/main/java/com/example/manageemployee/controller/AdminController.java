package com.example.manageemployee.controller;

import com.example.manageemployee.dao.userDAO.UserDAOImpl;
import com.example.manageemployee.dto.RoleDto;
import com.example.manageemployee.dto.UserDto;
import com.example.manageemployee.entity.Role;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserDAOServiceImpl userDAOServiceImpl;
    @Autowired
    UserDAOImpl userDAOImpl;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    //ModelAttribute
    @ModelAttribute("listrole")
    public  List<Role> Role(){
        List<Role> listrole = roleRepository.findAll();
        return listrole;
    }
    @ModelAttribute("userDto")
    public UserDto userDto() {
        return new UserDto();
    }
    @ModelAttribute("roledto")
    public RoleDto roleDto() {
        return new RoleDto();
    }

    //HTTP Method
    @GetMapping("/viewemployee")
    public String viewEmployee(Model model){
        List<UserDto> listUserDto = userDAOServiceImpl.findAll();
        model.addAttribute("listemployee",listUserDto);
        return "viewlistemployee";
    }
    @GetMapping("/formnewuser")
    public String formNewUser(){
        return "formnewuser";
    }

    @PostMapping("/formnewuser")
    public  String newUser(@ModelAttribute("userDto") UserDto udto, Model model) throws ParseException {
        if( udto.getFullname() == ""){
            model.addAttribute("msg","Fullname empty!");
            return "formnewuser";
        }
        if( udto.getUsername() == ""){
            model.addAttribute("msg","Username empty!");
            return "formnewuser";
        }
        if( udto.getPassword() == ""){
            model.addAttribute("msg","Password empty!");
            return "formnewuser";
        }
        if( udto.getAddress() == ""){
            model.addAttribute("msg","Address empty!");
            return "formnewuser";
        }
        if( udto.getDateofbirth() == ""){
            model.addAttribute("msg","DateOfBirth empty!");
            return "formnewuser";
        }
        if( udto.getRole() == null){
            model.addAttribute("msg","Select Role User");
            return "formnewuser";
        }

        if(userDAOServiceImpl.addUser(udto)){
            model.addAttribute("msg","Add Employee Successfully!");
            return "formnewuser";
        }
        else {
            model.addAttribute("msg","Add Employee Error!");
            return "formnewuser";
        }
    }
    @GetMapping("/editUser")
    public  String editUser(){
        return "newuser";
    }
    @GetMapping ("/deleteUser")
    public String deleteUser(@RequestParam String id, Model model){
        int idConvert = Integer.parseInt(id);
        List<UserDto> listEmployee = userDAOServiceImpl.deleteUser(idConvert);
        model.addAttribute("listemployee",listEmployee);
        return "viewlistemployee";
    }

}