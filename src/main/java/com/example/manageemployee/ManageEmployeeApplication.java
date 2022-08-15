package com.example.manageemployee;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class    ManageEmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageEmployeeApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
       return new ModelMapper();
    }
}
