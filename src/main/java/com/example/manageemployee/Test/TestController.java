package com.example.manageemployee.Test;

import com.example.manageemployee.jwt.JwtService;
import com.example.manageemployee.model.ProjectionInterface.IUserProjection;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.checkinservice.CheckinServiceImpl;
import com.example.manageemployee.service.roleservice.RoleServiceImpl;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.IUserDetailsServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    RoleServiceImpl roleDAOServiceImpl;
    @Autowired
    CheckinServiceImpl checkinDAOServiceImpl;
    @Autowired
    UserRepository userRepository;
    //@Scheduled(fixedRateString= "20000", initialDelay = 10000)
   // @Async
    public void thread1(){
        for(int i=1; i <= 10; i++) {
            System.out.println("User Thread" + i);
            try {
                Thread.sleep(1000); // sleep/stop a thread for 1 second
            } catch(InterruptedException e) {
                System.out.println("An Excetion occured: " + e);
            }
        }
    }
    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserDetailsServiceImpl userDAOService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserServiceImpl userDAOServiceImpl;

    @GetMapping("/test")
    public void Test(){
        userRepository.findBy(IUserProjection.class);

    }
    @GetMapping("/getuser")
    public List<User> getRoles(){
        List<User> userList = userRepository.getUsers();
        return userList;
    }
    @PostMapping("/resttemplate")
    public Todos Resttemplate(@RequestParam int id) {
        String url = "https://jsonplaceholder.typicode.com/todos/";
        //getForObject
        //Todos todos  =restTemplate.getForObject(url+id, Todos.class);
        ResponseEntity<Todos> todos = restTemplate.getForEntity(url + id, Todos.class);
        return todos.getBody();
    }



}
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Todos{
    private int id;
    private String title;
    private boolean completed;
    private int user_id;
}
