package com.example.manageemployee.service.mailservice;

import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
@Service
@PropertySources({@PropertySource("classpath:application-email.yml"),@PropertySource("classpath:application-cron.yml")})
public class MailService {
    private final LocalTime TIMECHECKIN = LocalTime.parse("08:30:00");
    private final LocalTime TIMECHECKOUT = LocalTime.parse("17:30:00");
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CheckinRepository checkinRepository;
    @Autowired
    private Environment env;
    @Value("${cron.report}")
    private String cronjob;

    public void SendMailCheckinLate(User user,String content){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("MAIL REPORT CHECKIN!");
        msg.setText(content);
        this.mailSender.send(msg);
    }
    public void sendMailRegister(User user){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("MAIL REGISTER SUCCESSFULLY!");
        msg.setText("You have successfully registered and logged in.");
        this.mailSender.send(msg);
    }
    public void sendMailFile(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        FileSystemResource file = new FileSystemResource(new File("src/main/java/com/example/manageemployee/service/mailService/mail.txt"));
        helper.addAttachment("Demo File",file);
        helper.setTo(user.getEmail());
        helper.setSubject("MAIL REGISTER SUCCESSFULLY!");
        helper.setText("You have successfully registered and logged in.");
        mailSender.send(mimeMessage);
    }
    @Scheduled(cron ="${cron.report}")
    public void scheduleTaskUsingCronExpression() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatDatecreated = new SimpleDateFormat("yyyy-MM-dd");
        List<User> listUser = userRepository.findAll();
        Date date_today = formatDatecreated.parse(formatDatecreated.format(new Date()));
        List<Checkin> list_checkin = checkinRepository.findAllByDatecreated(date_today);
        System.out.println(list_checkin.size());
        list_checkin.forEach(i->{
            LocalTime timeCheckin = LocalTime.parse(format.format(i.getTimecheckin()));
            if (timeCheckin.isBefore(TIMECHECKIN)){
                LocalTime timeCheckout = LocalTime.parse(format.format(i.getTimecheckout()));
                if(timeCheckout.isBefore(TIMECHECKOUT)){//checkout truoc gio
                    listUser.forEach(user->{
                        if(user.getCodecheckin()==i.getCodecheckin()){
                            String content1 = "check out som" + format.format(i.getTimecheckout());
                            listUser.remove(user);
                            SendMailCheckinLate(user,content1);
                        }
                    });
                }else{
                    List<User> list_user_checklate= userRepository.findAllByCodecheckin(i.getCodecheckin());
                    listUser.remove(list_user_checklate.get(0));
                }
            }else{//checkin muon
                if(i.getTimecheckout()!=null) { //
                    LocalTime timeCheckout = LocalTime.parse(format.format(i.getTimecheckout()));
                    if(timeCheckout.isBefore(TIMECHECKOUT)){//checkout truoc gio
                        listUser.forEach(user->{
                            if(user.getCodecheckin()==i.getCodecheckin()){
                                String content1 = "check out som" + format.format(i.getTimecheckout());
                                listUser.remove(user);
                                SendMailCheckinLate(user,content1);
                            }
                        });
                    }
                }else{//khong check out
                    listUser.forEach(user->{
                        if(user.getCodecheckin()==i.getCodecheckin()){
                            String content1 = "check out som" + format.format(i.getTimecheckout());
                            listUser.remove(user);
                            SendMailCheckinLate(user,content1);
                        }
                    });
                }
            }
        });
        listUser.forEach(user->{
            String contentnghilam = "Nghi lam ak ?";
            SendMailCheckinLate(user,contentnghilam);
        });
    }
}
