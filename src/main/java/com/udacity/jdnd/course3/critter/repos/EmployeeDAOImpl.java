package com.udacity.jdnd.course3.critter.repos;

import com.udacity.jdnd.course3.critter.DAO.EmployeeDAO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
@Transactional
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private final static String findEmployeesByDayAndSkills = "select distinct e.* from employee AS e inner join employee_skills AS skills ON skills.employee_id = e.id inner join employee_days_available AS days ON skills.employee_id = days.employee_id";

    @Override
    public List<EmployeeDTO> findEmployeesByDayAndSkills(int day, Set<EmployeeSkill> skills) {
        return jdbcTemplate.query(findEmployeesByDayAndSkills,
                                  new MapSqlParameterSource("day",day),
                                  new BeanPropertyRowMapper<>(EmployeeDTO.class));
    }
}
