package com.example.manageemployee.service.checkinservice;

import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.model.entity.ReportCheckin;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CheckinService {

    public List<ReportCheckin> reportCheckin(int codecheckin);
    public List<ReportCheckin> faultCheckin(int codecheckin);
    public List<Checkin> findAll();
    public List<Checkin> showReportCheckin();
    public List<ReportCheckin> statisticsReportCheckin() throws ParseException;
    public List<ReportCheckin> showReportCheckinByTime(Date startDate, Date endDate, int codecheckin);
    public List<ReportCheckin> showFaultCheckinByTime(Date startDate, Date endDate, int codecheckin);
}
