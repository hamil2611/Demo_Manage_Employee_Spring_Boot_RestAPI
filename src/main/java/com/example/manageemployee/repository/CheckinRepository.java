package com.example.manageemployee.repository;

import com.example.manageemployee.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin,Integer> {
    @Query("SELECT c FROM Checkin as c where c.codecheckin =:codecheckin order by c.timecheckin desc ")
    List<Checkin> findAllByCodecheckin(int codecheckin);
    List<Checkin> findAllByDatecreated(Date datecreated);
}

