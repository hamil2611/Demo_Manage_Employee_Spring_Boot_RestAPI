package com.example.manageemployee.controller;

import com.example.manageemployee.model.entity.user.SearchUser;
import com.example.manageemployee.model.entity.user.projection.IUserProjection;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.checkinservice.CheckinServiceImpl;
import com.example.manageemployee.service.userservice.UserService;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userDAOServiceImpl;
    private final CheckinServiceImpl checkinDAOServiceImpl;
    //HTTP Method
    @GetMapping("/viewemployee")
    public ResponseEntity<List<UserDto>> viewEmployee(){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("password "+userPrinciple.getPassword());
        List<UserDto> userDtoList = userDAOServiceImpl.findAllEmployee();
        System.out.println("***Before Return List (FETCH EAGER)***");
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }
    @GetMapping("/viewrole")
    public ResponseEntity<List<Role>> viewRole(){
        List<Role> roleList = userDAOServiceImpl.getRoles();
        System.out.println("***Before Return List (FETCH LAZY)***");
        return new ResponseEntity<>(roleList,HttpStatus.OK);
    }
    @GetMapping("/viewemployee/detail/{id}")
    public ResponseEntity<UserDto> viewEmployee(@PathVariable int id) {
        UserDto userDto = userDAOServiceImpl.getUser(id);
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/newuser")
    public  ResponseEntity newUser(@RequestBody UserDto udto) throws ParseException {
        if(userDAOServiceImpl.addUser(udto)){
            return ResponseEntity.ok("Add user successfully!");
        }
        else {
            return ResponseEntity.ok("Add user fail!");
        }
    }
    @PutMapping("/updateuser")
    public  ResponseEntity editUser(@RequestBody UserDto userDto){
        if(userDAOServiceImpl.updateUser(userDto))
            return ResponseEntity.ok("Update Successfully!");
        else {
            return ResponseEntity.ok("Update Fail!");
        }
    }
    @DeleteMapping("/deleteuser")
    public ResponseEntity deleteUser(@RequestParam int id){
        if(userDAOServiceImpl.deleteUser(id)){
            return ResponseEntity.ok("Delete User Successfully!");
        }
        else{
            return ResponseEntity.ok("Delete Fail!");
        }
    }
    @GetMapping("/statistics/reportcheckin")
    public ResponseEntity showReportcheckin(){
        try {
            return ResponseEntity.ok(checkinDAOServiceImpl.statisticsReportCheckin());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<List<UserDto>> searchEmployee(@RequestParam String name){
        return ResponseEntity.ok(userDAOServiceImpl.findUserByName(name));
    }
    //viewcheckin employee default week
    @GetMapping("/viewreportcheckin/{codecheckin}")
    public ResponseEntity<List<ReportCheckin>> viewCheckinEmployeeDefault(@PathVariable int codecheckin){
        return ResponseEntity.ok(checkinDAOServiceImpl.reportCheckin(codecheckin));
    }
    @PostMapping("/viewreportcheckin/{codecheckin}")
    public ResponseEntity<List<ReportCheckin>> viewCheckinEmployeeByTime(@RequestBody SortByTime sortByTime,@PathVariable int codecheckin ){
        return ResponseEntity.ok(checkinDAOServiceImpl.showReportCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(), codecheckin));
    }
    //viewfault checkin employee
    @GetMapping("/fault/{codecheckin}")
    public ResponseEntity showFaultCheckinEmployeeDefault(@PathVariable int codecheckin){
        return ResponseEntity.ok(checkinDAOServiceImpl.faultCheckin(codecheckin));
    }
    @PostMapping("/fault/{codecheckin}")
    public ResponseEntity<List<ReportCheckin>> showFaultCheckinEmployeeByTime(@RequestBody SortByTime sortByTime,@PathVariable int codecheckin){
        return ResponseEntity.ok(checkinDAOServiceImpl.showFaultCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(),codecheckin));
    }
    @PostMapping("/searchuser")
    public ResponseEntity<List<UserDto>> searchUserSpecification(@RequestBody SearchUser searchUser){
        return ResponseEntity.ok(userDAOServiceImpl.SearchUser(searchUser));
    }
    //Test
    @Autowired
    UserRepository userRepository;
    @Autowired
    CheckinRepository checkinRepository;
    @GetMapping("/userprojection")
    public List<IUserProjection> getUserPorjection(){
        List<IUserProjection> userProjectionList = userRepository.findBy(IUserProjection.class);
        return userProjectionList;
    }
    @GetMapping("/findallsort")
    public List<User> findAllSort(){
        Sort sort =Sort.by("id").descending();
        List<User> userList = userRepository.findAllSort(sort);
        return userList;
    }
    @GetMapping("/findallpage")
    public Page<User> findAllPage(){
        Pageable pageable = PageRequest.of(1, 2);
        Page<User> userPage = userRepository.findAllPage(pageable);
        return userPage;
    }
    @DeleteMapping("/deletecheckin/{id}")
    public void deleteCheckin(@PathVariable int id ){
        checkinRepository.deleteById(id);
    }
}