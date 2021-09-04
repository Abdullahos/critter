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

    /**
     * @param employee(id == null)
     * @return Employee(id!=null)
     */
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    /**
     * @param id
     * @return Employee of id or null if not exist.
     */
    public Employee findById(Long id){
        Optional<Employee> optionalEmployee =  employeeRepo.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else return null;
    }

    /**
     * @param skills
     * @param day
     * @return   list of Employees of these skills and day or empty list if not any.
     */
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

    /**
     * @param employee
     * @return EmployeeDTO
     */
    public EmployeeDTO employeeToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }

    /**
     * @param employeeDTO
     * @return Employee
     */
    public Employee DTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }
}
