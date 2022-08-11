package com.example.manageemployee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
    @OneToOne(mappedBy = "checkin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private ReportCheckin reportCheckin;
}
