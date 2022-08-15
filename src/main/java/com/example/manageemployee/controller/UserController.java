package com.example.manageemployee.controller;


import com.example.manageemployee.model.dto.OnLeaveDto;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.enummodel.EnumDurationOnleave;
import com.example.manageemployee.service.checkinservice.CheckinServiceImpl;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity viewCheckinDefault(){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.reportCheckin(userDAOServiceImpl.getCodecheckinByUsername());
        if (reportCheckinList.isEmpty()){
            System.out.println("null");
            return ResponseEntity.ok("NULL");
        }
        return ResponseEntity.ok(reportCheckinList);
    }
    @PostMapping("/viewcheckin")
    public List<ReportCheckin> viewCheckinByTime(@RequestBody SortByTime sortByTime ){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.showReportCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(), userDAOServiceImpl.getCodecheckinByUsername());
        return reportCheckinList;
    }
    @GetMapping("/fault")
    public ResponseEntity showFaultCheckinDefault(){

        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.faultCheckin(userDAOServiceImpl.getCodecheckinByUsername());
        if (reportCheckinList.isEmpty()){
            return ResponseEntity.ok("NULL");
        }
        return ResponseEntity.ok(reportCheckinList);
    }
    @PostMapping("/fault")
    public ResponseEntity showFaultCheckinDefault(@RequestBody SortByTime sortByTime){
        List<ReportCheckin> reportCheckinList = checkinDAOServiceImpl.showFaultCheckinByTime(sortByTime.getStarttime(),sortByTime.getEndtime(), userDAOServiceImpl.getCodecheckinByUsername());
        if (reportCheckinList.isEmpty()){
            return ResponseEntity.ok("NULL");
        }
        return ResponseEntity.ok(reportCheckinList);
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

