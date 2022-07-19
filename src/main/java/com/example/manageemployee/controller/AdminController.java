package com.example.manageemployee.controller;

import com.example.manageemployee.dao.userDAO.UserDAOImpl;
import com.example.manageemployee.dto.RoleDto;
import com.example.manageemployee.dto.UserDto;
import com.example.manageemployee.entity.Role;
import com.example.manageemployee.entity.User;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
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
    @Autowired
    UserDAOImpl userDAOImpl;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    //ModelAttribute
    @ModelAttribute("listrole")
    public  List<Role> Role(){
        List<Role> listrole = roleRepository.findAll();
        return listrole;
    }
    @ModelAttribute("userform")
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
        List<User> list = userRepository.findAll();
        //list.forEach(i -> System.out.println(i.getFullname()));
        //Lấy ROLE_EMPLOYEE
        Optional<Role> role = roleRepository.findById(2);
        //Tim User theo ROLE_EMPLOYEE
        List<User> listEmployee= userRepository.findAllByRole(role);
        listEmployee.forEach(i -> System.out.println(i.getFullname()));
        List<UserDto> listuserdto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        //listuserdto.forEach(i-> System.out.println(i.getEmail()));

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
    @GetMapping("/formnewuser")
    public String formNewUser(){
        return "formnewuser";
    }

    @PostMapping("/formnewuser")
    public  String newUser(@ModelAttribute("userform") UserDto udto, Model model){
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
        User u = this.modelMapper.map(udto,User.class);
        Date date = new Date();
        int codecheckin = ThreadLocalRandom.current().nextInt(1000,10000);
        List<User> user_checkCodeCheckin = userRepository.findAllByCodecheckin(codecheckin);
        while (user_checkCodeCheckin.isEmpty()!=true){
            codecheckin = ThreadLocalRandom.current().nextInt(1000,10000);
            user_checkCodeCheckin = userRepository.findAllByCodecheckin(codecheckin);
        }
        u.setCodecheckin(codecheckin);
        u.setDatecreated(date);
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userDAOImpl.newUser(u);
        model.addAttribute("msg","Thêm thành công");
        System.out.println("***End!***");
        return "formnewuser";
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
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam String id, Model model){
        System.out.println("id"+id);
        int idConvert = Integer.parseInt(id);
        Optional<User> usercheck = userRepository.findById(idConvert);
        if(usercheck.isEmpty()){
            List<User> list = userRepository.findAll();
            Optional<Role> role = roleRepository.findById(1);
            List<User> listEmployee= userRepository.findAllByRole(role);
            listEmployee.forEach(i -> System.out.println(i.getFullname()));
            List<UserDto> listuserdto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
            model.addAttribute("listemployee",listEmployee);
            return "viewlistemployee";
        }
        userRepository.deleteById(idConvert);
        List<User> list = userRepository.findAll();
        Optional<Role> role = roleRepository.findById(1);
        List<User> listEmployee= userRepository.findAllByRole(role);
        listEmployee.forEach(i -> System.out.println(i.getFullname()));
        List<UserDto> listuserdto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        model.addAttribute("listemployee",listEmployee);
        return "viewlistemployee";
    }

}

//Note
//        Optional<User> userBCrypt = userRepository.findById(22);
//        User userBCryptConvert = this.modelMapper.map(userBCrypt,User.class);
//        System.out.println(bCryptPasswordEncoder.matches("dinhann",userBCryptConvert.getPassword()));