package com.api.webvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.webvote.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

}
