package com.example.manageemployee.Test;

import com.example.manageemployee.jwt.JwtService;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.checkinDAOService.CheckinDAOServiceImpl;
import com.example.manageemployee.service.roleDAOService.RoleDAOServiceImpl;
import com.example.manageemployee.service.userDAOService.UserDAOServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.IUserDetailsServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserDetailsServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    UserDAOServiceImpl userDAOServiceImpl;

    @PostMapping("/test")
    public void Test(){
        userDAOServiceImpl.joinPorject(40,1);
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
    private static final String SECRET_KEY = "123456789";
    private static final long EXPIRE_TIME = 86400000000L;
    @Autowired
    GoogleUtils googleUtils;
    @Autowired
    private IUserDetailsServiceImpl userService;
    @RequestMapping("/loginwithgoogle")
    public String loginGoogle(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
//        if (code == null || code.isEmpty()) {
//            return "redirect:/login?google=error";
//        }
        System.out.println("Code login with google:" + code);
        String accessToken = googleUtils.getToken(code);
        System.out.println("Get Token Complete!");
        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        System.out.println("Get UserInfo Complete!");
        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        System.out.println("Get buildUser Complete!");
        System.out.println("loginGoogle" + userDetail.getUsername());
        User user = userRepository.findByEmail(googlePojo.getEmail());
        String jwt = Jwts.builder()
                .setSubject((user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        System.out.println(jwt);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        UserPrinciple userPrinciple =(UserPrinciple) authentication.getPrincipal();
        System.out.println("doFilter Test ContextHolder: "+ userPrinciple.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userPrinciple.getUsername();
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
