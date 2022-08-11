package com.example.manageemployee.controller;


import com.example.manageemployee.model.dto.OnLeaveDto;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.enummodel.EnumDurationOnleave;
import com.example.manageemployee.model.enummodel.EnumStatus;
import com.example.manageemployee.service.checkinservice.CheckinServiceImpl;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userDAOServiceImpl;
    @Autowired
    CheckinServiceImpl checkinDAOServiceImpl;
    @GetMapping("/viewcheckin")
    public List<ReportCheckin> viewCheckin(){
        Calendar calendar = Calendar.getInstance();
        int weekofyear = calendar.get(Calendar.WEEK_OF_YEAR);
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.findReportCheckinByCodecheckin(userDAOServiceImpl.getCodecheckinByUsername());
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
    @PostMapping("/viewcheckin")
    public List<ReportCheckin> viewCheckin(@RequestBody SortByTime sortByTime ){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.ShowReportCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(), userDAOServiceImpl.getCodecheckinByUsername());
        return reportCheckinList;
    }
    @GetMapping("/fault")
    public List<ReportCheckin> showFaultCheckinDefault(){
        Calendar calendar = Calendar.getInstance();
        int monthofyear = calendar.get(Calendar.MONTH)+1;
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.findReportCheckinByCodecheckin(userDAOServiceImpl.getCodecheckinByUsername());
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
    @PostMapping("/fault")
    public List<ReportCheckin> showFaultCheckinDefault(@RequestBody SortByTime sortByTime){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.ShowReportCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(), userDAOServiceImpl.getCodecheckinByUsername());
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
    @PostMapping("/myonleave")
    public boolean sendOnLeave(@RequestBody OnLeaveDto onLeaveDto){
        Date date = new Date();
        System.out.println(EnumDurationOnleave.WEEK);
        onLeaveDto.setDurationonleave(EnumDurationOnleave.WEEK);
        System.out.println(onLeaveDto.getDurationonleave());
        System.out.println(userDAOServiceImpl.sendRequestOnLeave(onLeaveDto));
        return false;
    }
}
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class SortByTime{
    private Date starttime,endtime;
}

