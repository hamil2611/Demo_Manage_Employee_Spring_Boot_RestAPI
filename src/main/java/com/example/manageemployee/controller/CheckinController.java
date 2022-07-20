package com.example.manageemployee.controller;

import com.example.manageemployee.dto.CheckinDto;
import com.example.manageemployee.entity.Checkin;
import com.example.manageemployee.mailservice.MailController;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CheckinController {
    @Autowired
    CheckinRepository checkinRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailController mailController;
    @Autowired
    ModelMapper modelMapper;
    @ModelAttribute("checkinform")
    public CheckinDto checkin(){
        return new CheckinDto();
    }
    @GetMapping("/checkin")
    public String employeeCheckin(){

        return "checkinform";
    }
    @PostMapping("/checkin")
    public String employeeCheckin(@ModelAttribute("checkinform") CheckinDto checkinDto, Model model){
        Checkin checkin = this.modelMapper.map(checkinDto,Checkin.class);
        if(userRepository.findAllByCodecheckin(checkinDto.getCodecheckin()).isEmpty()!=true){
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            String today = format.format(new Date());

            List<Checkin> listcheckin = checkinRepository.findAllByCodecheckin(checkin.getCodecheckin());
            //Check xem employee da checkin trong ngay do chua
            if(listcheckin.isEmpty()!=true){
                Checkin checkin_check_Today = listcheckin.get(0);
                String date_check_Today = format.format(checkin_check_Today.getDatecreated());
                if(date_check_Today.equals(today)){
                    checkin_check_Today.setTimecheckout(new Date());
                    checkinRepository.save(checkin_check_Today);
                    //mailController.SendMail(userRepository.findAllByCodecheckin(checkin.getCodecheckin()).get(0));
                }else{
                    checkin.setTimecheckin(new Date());
                    checkin.setDatecreated(new Date());
                    checkinRepository.save(checkin);
                }
            }else {
                checkin.setDatecreated(new Date());
                checkin.setTimecheckin(new Date());
                checkinRepository.save(checkin);
            }
        }else{
            model.addAttribute("msgreport","CODECHECKIN DOES NOT EXIST");
            return "checkinform";
        }
        model.addAttribute("msgreport","CHECKIN SUCCESSFULLY!");
        model.addAttribute("msghello", "HELLO!   "
                + userRepository.findAllByCodecheckin(checkin.getCodecheckin()).get(0).getFullname());
        return "checkinform";
    }
    @GetMapping("/viewcheckin")
    public String ViewCheckin(@RequestParam int id, Model model){
        List<Checkin> listcheckin = checkinRepository.findAllByCodecheckin(id);
        model.addAttribute("listcheckin",listcheckin);
        return "viewcheckin";
    }
}
