package com.example.manageemployee.service.googleservice;

import com.example.manageemployee.jwt.JwtResponse;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.IUserDetailsServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Service
public class GoogleService {
    @Autowired
    GoogleUtils googleUtils;
    @Autowired
    UserServiceImpl userDAOServiceImpl;
    @Autowired
    private IUserDetailsServiceImpl userService;
    private static final String SECRET_KEY = "123456789";
    private static final long EXPIRE_TIME = 86400000000L;

    public ResponseEntity<?> googleLogin(HttpServletRequest request) throws IOException {
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
        System.out.println("loginGoogle: " + userDetail.getUsername());
        UserDto userDto = userDAOServiceImpl.getUserByEmail(googlePojo.getEmail());
        if (userDto==null){
            System.out.println("EMAIL DOES NOT EXIST!");
            return ResponseEntity.ok("EMAIL DOES NOT EXIST!");
        }

        String jwt = Jwts.builder()
                .setSubject((userDto.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        System.out.println(jwt);
        UserDetails userDetails = userService.loadUserByUsername(userDto.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        UserPrinciple userPrinciple =(UserPrinciple) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt,"Bearer",userDetails.getUsername(), userDetails.getAuthorities() ));
    }
}
