package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.repos.EmployeeRepo;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;

@Transactional
@Service
public class EmployeeService{
    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee findById(Long id){
        Optional<Employee> optionalEmployee =  employeeRepo.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else throw new InputMismatchException("no such employee id");
    }
    public List<Employee> findBySkillsAndDay(Set<EmployeeSkill> skills, DayOfWeek day){

        List<Employee> employees = employeeRepo.findByDaysAvailableContaining(day);
        List<Employee>capableEmps = new ArrayList<>();
        employees.forEach(employee -> {
            if(employee.getSkills().containsAll(skills)){
                capableEmps.add(employee);
            }
        });
        return capableEmps;
    }
    /**this method convert to DTO**/
    public EmployeeDTO employeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }
    /**from DTO to Employee**/
    public Employee DTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }
}
