package com.example.manageemployee.model.entity;

import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.model.enummodel.EnumDurationOnleave;
import com.example.manageemployee.model.enummodel.EnumTypeOnLeave;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="onleave")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private EnumTypeOnLeave typeonleave;
    private String reason;
    private EnumDurationOnleave durationonleave;
    private Date datecreated;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
}
