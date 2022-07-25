package com.example.manageemployee.service.mailService;

import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Controller
@PropertySources({@PropertySource("classpath:application-email.yml")})
public class MailController {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CheckinRepository checkinRepository;
    @Autowired
    private ConfigurableEnvironment env;
    @Value("${scheduled.cronjob}")
    private String cronjob;
;
    public void SendMailCheckinLate(User user,String content){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("Đây là mail test");
        msg.setText(content);
        javaMailSender.send(msg);
    }
    @Scheduled(cron ="${scheduled.cronjob}")
    public void scheduleTaskUsingCronExpression() throws ParseException {
        System.out.println(cronjob);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        List<User> list_user = userRepository.findAll();
        SimpleDateFormat format_datecreated = new SimpleDateFormat("yyyy-MM-dd");
        Date date_today = format_datecreated.parse(format_datecreated.format(new Date()));
        List<Checkin> list_checkin = checkinRepository.findAllByDatecreated(date_today);
        System.out.println(list_checkin.size());
        list_checkin.forEach(i->{
            try {
                String content="";
                Date time_checkin = format.parse(format.format(i.getTimecheckin()));
                if (time_checkin.before(format.parse("08:30:00"))){
                    System.out.println("true");
                    Date time_checkout = format.parse(format.format(i.getTimecheckout()));
                    if(time_checkout.before(format.parse("17:30:00"))){//checkout truoc gio
                        content = "check out som" + format.format(i.getTimecheckout());
                        List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                        list_user.remove(list_user_checklate.get(0));
                        SendMailCheckinLate(list_user_checklate.get(0),content);
                    }else{
                        List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                        list_user.remove(list_user_checklate.get(0));
                    }
                }else{//checkin muon
                    if(i.getTimecheckout()!=null) { //
                        Date time_checkout = format.parse(format.format(i.getTimecheckout()));
                        if(time_checkout.before(format.parse("17:30:00"))){//checkout truoc gio
                            content = "Đi làm muộn"+ format.format(i.getTimecheckin())+ "và check out som" + format.format(i.getTimecheckout());
                            List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                            list_user.remove(list_user_checklate.get(0));
                            SendMailCheckinLate(list_user_checklate.get(0),content);
                        }
                    }else{//khong check out
                        content = "Đi làm muộn và không check out";
                        List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                        list_user.remove(list_user_checklate.get(0));
                        SendMailCheckinLate(list_user_checklate.get(0),content);
                    }
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        list_user.forEach(user->{
            String content_nghilam = "Nghi lam ak ?";
            SendMailCheckinLate(user,content_nghilam);
        });
    }
}
