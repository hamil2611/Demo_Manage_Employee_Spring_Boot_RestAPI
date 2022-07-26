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
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@PropertySources({@PropertySource("classpath:application-email.yml"),@PropertySource("classpath:application-cron.yml")})
public class MailService {
    @Autowired
    JavaMailSender mailSender;
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
        msg.setSubject("MAIL REPORT CHECKIN!");
        msg.setText(content);
        this.mailSender.send(msg);
    }
    public void SendMailRegister(User user){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("MAIL REGISTER SUCCESSFULLY!");
        msg.setText("You have successfully registered and logged in.");
        this.mailSender.send(msg);
    }
    @Scheduled(cron ="${scheduled.cronjob}")
    public void scheduleTaskUsingCronExpression() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatDatecreated = new SimpleDateFormat("yyyy-MM-dd");
        List<User> listUser = userRepository.findAll();
        Date date_today = formatDatecreated.parse(formatDatecreated.format(new Date()));
        List<Checkin> list_checkin = checkinRepository.findAllByDatecreated(date_today);
        System.out.println(list_checkin.size());
        list_checkin.forEach(i->{
            try {
                String content="";
                Date timeCheckin = format.parse(format.format(i.getTimecheckin()));
                if (timeCheckin.before(format.parse("08:30:00"))){
                    System.out.println("true");
                    Date timeCheckout = format.parse(format.format(i.getTimecheckout()));
                    if(timeCheckout.before(format.parse("17:30:00"))){//checkout truoc gio
                        content = "check out som" + format.format(i.getTimecheckout());
                        List<User> listUserChecklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                        listUser.remove(listUserChecklate.get(0));
                        SendMailCheckinLate(listUserChecklate.get(0),content);
                    }else{
                        List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                        listUser.remove(list_user_checklate.get(0));
                    }
                }else{//checkin muon
                    if(i.getTimecheckout()!=null) { //
                        Date timeCheckout = format.parse(format.format(i.getTimecheckout()));
                        if(timeCheckout.before(format.parse("17:30:00"))){//checkout truoc gio
                            content = "Đi làm muộn"+ format.format(i.getTimecheckin())+ "và check out som" + format.format(i.getTimecheckout());
                            List<User> listUserChecklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                            listUser.remove(listUserChecklate.get(0));
                            SendMailCheckinLate(listUserChecklate.get(0),content);
                        }
                    }else{//khong check out
                        content = "Đi làm muộn và không check out";
                        List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                        listUser.remove(list_user_checklate.get(0));
                        SendMailCheckinLate(list_user_checklate.get(0),content);
                    }
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        listUser.forEach(user->{
            String contentnghilam = "Nghi lam ak ?";
            SendMailCheckinLate(user,contentnghilam);
        });
    }
}
