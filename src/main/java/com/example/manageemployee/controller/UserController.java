package com.example.manageemployee.controller;


import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.enummodel.EnumStatus;
import com.example.manageemployee.repository.ReportCheckinRepository;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOServiceImpl;
import com.example.manageemployee.service.mailService.MailService;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDAOServiceImpl userDAOServiceImpl;
    @Autowired
    CheckinDAOServiceImpl checkinDAOServiceImpl;
    @GetMapping("/viewcheckin")
    public List<ReportCheckin> ViewCheckin(@RequestParam int id){
        Calendar calendar = Calendar.getInstance();
        int weekofyear = calendar.get(Calendar.WEEK_OF_YEAR);
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.findReportCheckinByCodecheckin(id);
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
        System.out.println("notnull");
        return reportCheckinWeekNowList;
    }
    @GetMapping("/fault")
    public List<ReportCheckin> ShowFaultCheckinDefault(@RequestParam int id){
        Calendar calendar = Calendar.getInstance();
        int monthofyear = calendar.get(Calendar.MONTH)+1;
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.findReportCheckinByCodecheckin(id);
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
        return faultCheckinList;
    }
}

