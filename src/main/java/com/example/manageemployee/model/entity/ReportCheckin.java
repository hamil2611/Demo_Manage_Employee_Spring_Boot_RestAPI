package com.example.manageemployee.model.entity;

import com.example.manageemployee.model.enummodel.EnumReport;
import com.example.manageemployee.model.enummodel.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="reportcheckin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportCheckin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int solvetimecheckin;
    private int solvetimecheckout;
    @Enumerated(EnumType.STRING)
    private EnumReport reportcheckin;
    @Enumerated(EnumType.STRING)
    private EnumReport reportcheckout;
    private String description;
    private String Complain;
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="checkin_id")
    private Checkin checkin;
}
