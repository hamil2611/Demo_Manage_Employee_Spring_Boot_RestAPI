package com.example.manageemployee.service.checkinDAOService;

import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.model.entity.ReportCheckin;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.model.enummodel.EnumReport;
import com.example.manageemployee.model.enummodel.EnumStatus;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.ReportCheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.webConfig.securityConfig.UserPrinciple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;;
import java.util.Date;
import java.util.List;

@Service
@PropertySources({@PropertySource("classpath:application-email.yml"),@PropertySource("classpath:application-cron.yml")})
public class    CheckinDAOServiceImpl implements CheckinDAOService{
    @Autowired
    CheckinRepository checkinRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReportCheckinRepository reportCheckinRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ReportCheckin> findReportCheckinByCodecheckin(int codecheckin) {
        List<ReportCheckin> listReportCheckin = reportCheckinRepository.findReportcheckin(codecheckin);
        return listReportCheckin;
    }

    @Override
    public List<Checkin> findAll() {
        return checkinRepository.findAll();
    }

    @Override
    public List<Checkin> ShowReportCheckin() {
        return null;
    }
    @Override
    //@Async
    //@Scheduled(cron ="${scheduled.cronjob}")
    public List<ReportCheckin> StatisticsReportCheckin()  throws ParseException {
        SimpleDateFormat formatDatecreated = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date dateToday = formatDatecreated.parse(formatDatecreated.format(new Date()));
        List<Checkin> listCheckin = checkinRepository.findAllByDatecreated(dateToday);
        listCheckin.forEach(checkin -> {
            ReportCheckin reportCheckin = new ReportCheckin();
            String timeCheckin = format.format(checkin.getTimecheckin());
            int tmp=0;
            int hour = 0;
            int minute = 0;
            int second = 0;
            //Checkin
            for(String i:timeCheckin.split(":")){
                if(tmp==0){
                    hour= Integer.parseInt(i)*60*60;
                    tmp++;
                }
                else if(tmp==1){
                    minute = Integer.parseInt(i)*60;
                    tmp++;
                }
                else {
                    second=Integer.parseInt(i);
                }
            }
            int totaltime = hour + minute + second;
            int totaltimeDefault = 8*60*60 + 30*60;
            int deviantTime = totaltime-totaltimeDefault;
            if(deviantTime<=0){
                reportCheckin.setSolvetimecheckin(deviantTime/60);
                reportCheckin.setReportcheckin(EnumReport.OK);
                reportCheckin.setStatus(EnumStatus.OK);
            }else{
                reportCheckin.setSolvetimecheckin(deviantTime/60);
                reportCheckin.setReportcheckin(EnumReport.CHECKIN_LATE);
                reportCheckin.setStatus(EnumStatus.NOTOK);
            }
            //Checkout
            for(String i:timeCheckin.split(":")){
                if(tmp==0){
                    hour= Integer.parseInt(i)*60*60;
                    tmp++;
                }
                else if(tmp==1){
                    minute = Integer.parseInt(i)*60;
                    tmp++;
                }
                else {
                    second=Integer.parseInt(i);
                }
            }
            totaltime = hour + minute + second;
            totaltimeDefault = 17*60*60 + 30*60;
            deviantTime = totaltime-totaltimeDefault;
            if(deviantTime>=0){
                reportCheckin.setSolvetimecheckout(deviantTime/60);
                reportCheckin.setReportcheckout(EnumReport.OK);
                reportCheckin.setStatus(EnumStatus.OK);
            }else {
                reportCheckin.setSolvetimecheckout(deviantTime/60);
                reportCheckin.setReportcheckout(EnumReport.CHECKOUT_EARLY);
                reportCheckin.setStatus(EnumStatus.NOTOK);
            }
            reportCheckin.setCheckin(checkin);
            reportCheckinRepository.save(reportCheckin);
        });
        List<ReportCheckin> listReportCheckin = reportCheckinRepository.findAll();
        return  listReportCheckin;
    }

    @Override
    public List<ReportCheckin> ShowReportCheckinByTime(Date startDate, Date endDate,int codecheckin) {
        List<ReportCheckin> reportCheckinList = reportCheckinRepository.findReportCheckinByTime(startDate,endDate,codecheckin);
        System.out.println("Size reportcheckin: "+reportCheckinList.size());
        return reportCheckinList;
    }

}
