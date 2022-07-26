package com.example.manageemployee.webConfig.securityConfig;

import com.example.manageemployee.jwt.JwtEntryPoint;
import com.example.manageemployee.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtEntryPoint jwtEntryPoint;
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home","/login","/logout","/test","/admin/**","/checkin/**").permitAll();

//        http.authorizeRequests()
//                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .antMatchers("/checkin/**").access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')");

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/login");

        http.formLogin().loginPage("/login")
                .defaultSuccessUrl("/login?successfully")//đây Khi đăng nhập thành công thì vào trang này. userAccountInfo sẽ được khai báo trong controller để hiển thị trang view tương ứng
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().permitAll();

        http.csrf().disable();
    }

}
