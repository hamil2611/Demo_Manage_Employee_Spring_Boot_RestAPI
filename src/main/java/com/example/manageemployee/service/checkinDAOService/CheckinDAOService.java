package com.example.manageemployee.service.checkinDAOService;

import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.model.entity.ReportCheckin;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CheckinDAOService {
    public List<ReportCheckin> findReportCheckinByCodecheckin(int codecheckin);
    public List<Checkin> findAll();
    public List<Checkin> ShowReportCheckin();
    public List<ReportCheckin> StatisticsReportCheckin() throws ParseException;
    public List<ReportCheckin> ShowReportCheckinByTime(Date startDate, Date endDate, int codecheckin);
}
