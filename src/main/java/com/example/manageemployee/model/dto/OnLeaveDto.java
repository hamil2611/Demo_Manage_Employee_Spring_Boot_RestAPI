package com.example.manageemployee.model.dto;

import com.example.manageemployee.model.enummodel.EnumDurationOnleave;
import com.example.manageemployee.model.enummodel.EnumTypeOnLeave;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnLeaveDto {
    private int id;
    private EnumTypeOnLeave typeonleave;
    private String reason;
    private Date datecreated;
    private EnumDurationOnleave durationonleave;
}