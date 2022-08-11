package com.example.manageemployee.repository;

import com.example.manageemployee.model.entity.OnLeave;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Cacheable;
import java.util.List;

public interface OnLeaveRepository extends JpaRepository<OnLeave,Integer> {
//    @Cacheable
//    public List<OnLeave> getOnLeave();
//
//    @CacheEvict
//    <S extends OnLeave> S save(S s);

}
