package com.example.manageemployee.model.dto;

import com.example.manageemployee.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    private String fullname;
    private String dateofbirth;
    private String address;
    private String username,password;
    private String email;
    private int codecheckin;
    private Date datecreated;
    private Role role;
}
