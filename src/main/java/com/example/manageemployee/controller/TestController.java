package com.example.manageemployee.controller;

import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOService;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOServiceImpl;
import com.example.manageemployee.service.roleDAOService.RoleDAOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;


class UserThread extends Thread {
    public void run() {
        for(int i=1; i <= 10; i++) {
            System.out.println("User Thread" + i);
            try {
                Thread.sleep(1000); // sleep/stop a thread for 1 second
            } catch(InterruptedException e) {
                System.out.println("An Excetion occured: " + e);
            }
        }
    }
}
@RestController
public class TestController {
    @Autowired
    RoleDAOServiceImpl roleDAOServiceImpl;
    @Autowired
    CheckinDAOServiceImpl checkinDAOServiceImpl;
    @Autowired
    UserRepository userRepository;
    @Scheduled(cron = "5-15/1 25-27 16 * * ?")
    @Async
    public void thread1(){
        System.out.println(1);
//        for(int i=1; i <= 10; i++) {
//            System.out.println("User Thread" + i);
//            try {
//                Thread.sleep(1000); // sleep/stop a thread for 1 second
//            } catch(InterruptedException e) {
//                System.out.println("An Excetion occured: " + e);
//            }
//        }
    }
}
