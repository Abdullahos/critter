package com.udacity.jdnd.course3.critter.DAO;

import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ScheduleDAO {
    List<ScheduleDTO> findByEmployeeId(Long employeeId);
}
