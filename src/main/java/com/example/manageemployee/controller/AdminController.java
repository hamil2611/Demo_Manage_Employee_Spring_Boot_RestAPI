package com.example.manageemployee.controller;

import com.example.manageemployee.dto.UserDto;
import com.example.manageemployee.entity.Role;
import com.example.manageemployee.entity.User;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;

    @ModelAttribute("test")
    public List<String> test(){
        List list = new ArrayList<>();
        list.add("1");
        list.add("2");
        return list;
    }
    @GetMapping("/viewemployee")
    public String viewEmployee(Model model){
        List<User> list = userRepository.findAll();
        list.forEach(i -> System.out.println(i.getFullname()));
        //Lấy ROLE_EMPLOYEE
        Optional<Role> role = roleRepository.findById(1);
        //Tim User theo ROLE_EMPLOYEE
        List<User> listEmployee= userRepository.findAllByRole(role);

        listEmployee.forEach(i -> System.out.println(i.getFullname()));

        List<UserDto> listuserdto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        listuserdto.forEach(i-> System.out.println(i.getEmail()));

        model.addAttribute("listemployee",listEmployee);
        return "viewlistemployee";
    }
    @GetMapping("/viewcheckin")
    public String viewCheckin(@ModelAttribute("user") @RequestParam int id, Model model){
        Optional<User> user = userRepository.findById(id);
        User usernew = this.modelMapper.map(user,User.class);
        System.out.println(usernew.getEmail());
        model.addAttribute("user",usernew);
        return "viewcheckin";
    }

}
