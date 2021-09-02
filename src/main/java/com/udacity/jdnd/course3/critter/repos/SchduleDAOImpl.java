package com.udacity.jdnd.course3.critter.repos;

import com.udacity.jdnd.course3.critter.DAO.ScheduleDAO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchduleDAOImpl implements ScheduleDAO {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private final static String findByEmployeeId = "select s from schedule as s inner join schedule_employee as se on s.id = se.schedule_id where se.employee_id=:employeeId";
    @Override
    public List<ScheduleDTO> findByEmployeeId(Long employeeId) {
        return jdbcTemplate.query(findByEmployeeId,
                new MapSqlParameterSource("employee_id", employeeId),
                new BeanPropertyRowMapper<>(ScheduleDTO.class)
        );
    }
}
