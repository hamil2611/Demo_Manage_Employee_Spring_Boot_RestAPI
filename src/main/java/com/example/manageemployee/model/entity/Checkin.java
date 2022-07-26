package com.example.manageemployee.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Checkin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date timecheckin;
    private Date timecheckout;
    private int codecheckin;
    private Date datecreated;
    private int dayofweek;
    private int weekofyear;
    private int monthofyear;
    private int year;
}
