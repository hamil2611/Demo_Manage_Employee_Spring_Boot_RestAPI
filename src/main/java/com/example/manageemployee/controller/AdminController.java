package com.example.manageemployee.controller;

import com.example.manageemployee.model.ProjectionInterface.IUserProjection;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.model.enummodel.EnumStatus;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.checkinservice.CheckinServiceImpl;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImpl userDAOServiceImpl;
    private final CheckinServiceImpl checkinDAOServiceImpl;
    //HTTP Method
    @GetMapping("/viewemployee")
    public ResponseEntity<List<UserDto>> viewEmployee(){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userPrinciple.getPassword());
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
    public UserDto viewEmployee(@PathVariable int id) {
        UserDto userDto = userDAOServiceImpl.getUser(id);
        return userDto;
    }
    @PostMapping("/newuser")
    public  String newUser(@RequestBody UserDto udto) throws ParseException {
        if(userDAOServiceImpl.addUser(udto)){
            return "Add user successfully!";
        }
        else {
            return "Add user fail!";
        }
    }
    @PutMapping("/updateuser")
    public  String editUser(@RequestBody UserDto userDto){
        if(userDAOServiceImpl.updateUser(userDto))
            return "Update Successfully!";
        else {
            return "Update Fail!";
        }
    }
    @DeleteMapping("/deleteuser")
    public String deleteUser(@RequestParam int id){
        if(userDAOServiceImpl.deleteUser(id)){
            return "Delete User Successfully!";
        }
        else{
            return "Delete Fail!";
        }
    }
    @GetMapping("/statistics/reportcheckin")
    public List<ReportCheckin> showReportcheckin(){
        try {
            return checkinDAOServiceImpl.StatisticsReportCheckin();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/search")
    public List<UserDto> SearchEmployee(@RequestParam String name){
        if(name.equals(""))
            return null;
        List<UserDto> userDtoList = userDAOServiceImpl.findUserByName(name);
        return userDtoList;
    }
    //viewcheckin employee default week
    @GetMapping("/viewreportcheckin/{codecheckin}")
    public List<ReportCheckin> ViewCheckin(@PathVariable int codecheckin){
        Calendar calendar = Calendar.getInstance();
        int weekofyear = calendar.get(Calendar.WEEK_OF_YEAR);
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.findReportCheckinByCodecheckin(codecheckin);
        if (reportCheckinList.isEmpty()){
            System.out.println("null");
            return null;
        }
        List<ReportCheckin> reportCheckinWeekNowList = new ArrayList<>();
        reportCheckinList.forEach(reportCheckin -> {
            if (reportCheckin.getCheckin().getWeekofyear()==weekofyear) {
                reportCheckinWeekNowList.add(reportCheckin);
            }
        });
        return reportCheckinWeekNowList;
    }
    @PostMapping("/viewreportcheckin/{codecheckin}")
    public List<ReportCheckin> ViewCheckin(@RequestBody SortByTime sortByTime,@PathVariable int codecheckin ){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.ShowReportCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(), codecheckin);
        return reportCheckinList;
    }
    //viewfault checkin employee
    @GetMapping("/fault/{codecheckin}")
    public ResponseEntity<List<ReportCheckin>> ShowFaultCheckinDefault(@PathVariable int codecheckin){
        Calendar calendar = Calendar.getInstance();
        int monthofyear = calendar.get(Calendar.MONTH)+1;
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.findReportCheckinByCodecheckin(codecheckin);
        if (reportCheckinList.isEmpty()){
            System.out.println("null");
            return null;
        }
        List<ReportCheckin> faultCheckinList = new ArrayList<>();
        reportCheckinList.forEach(reportCheckin -> {
            if (reportCheckin.getCheckin().getMonthofyear()==monthofyear&&reportCheckin.getStatus().equals(EnumStatus.NOTOK)) {
                faultCheckinList.add(reportCheckin);
            }
        });
        return ResponseEntity.ok(faultCheckinList);
    }
    @PostMapping("/fault/{codecheckin}")
    public List<ReportCheckin> ShowFaultCheckinDefault(@RequestBody SortByTime sortByTime,@PathVariable int codecheckin){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.ShowReportCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(),codecheckin);
        if (reportCheckinList.isEmpty()){
            System.out.println("null");
            return null;
        }
        List<ReportCheckin> faultCheckinList = new ArrayList<>();
        reportCheckinList.forEach(reportCheckin -> {
            if (reportCheckin.getStatus().equals(EnumStatus.NOTOK)) {
                faultCheckinList.add(reportCheckin);
            }
        });
        return faultCheckinList;
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