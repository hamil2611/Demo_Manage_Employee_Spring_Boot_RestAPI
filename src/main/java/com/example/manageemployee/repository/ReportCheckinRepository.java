package com.example.manageemployee.repository;

import com.example.manageemployee.model.entity.ReportCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportCheckinRepository extends JpaRepository<ReportCheckin,Integer> {
    @Query(value = "select * " +
                    "from checkin,reportcheckin " +
                    "where " +
                    "reportcheckin.checkin_id=checkin.id and checkin.codecheckin=:codecheckin"
                    ,nativeQuery = true)
    List<ReportCheckin> findReportcheckin(int codecheckin);
}
