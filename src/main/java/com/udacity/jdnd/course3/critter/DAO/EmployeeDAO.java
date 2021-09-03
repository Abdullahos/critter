package com.udacity.jdnd.course3.critter.DAO;

import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
@Transactional
@Repository
public interface EmployeeDAO {
    List<EmployeeDTO> findEmployeesByDayAndSkills(int day, Set<EmployeeSkill> skills);
}
