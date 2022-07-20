package com.example.manageemployee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckinDto {
    private int id;
    private Date timecheckin;
    private Date timecheckout;
    private int codecheckin;
    private Date datecreated;
}
