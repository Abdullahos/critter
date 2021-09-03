package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Transactional
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        //some dummies assumption, can be configured when understand the requirements more clearly
        if (employeeDTO.getName() == null || employeeDTO.getSkills() == null) throw new UnsupportedOperationException();
        else {
            Employee employee = employeeService.DTOToEmployee(employeeDTO);
            EmployeeDTO retrievedEmployee = employeeService.employeeToDTO(employeeService.save(employee));
            return retrievedEmployee;
        }
    }
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee retrievedEmployee =  employeeService.findById(employeeId);
        EmployeeDTO dto = employeeService.employeeToDTO(retrievedEmployee);
        return dto;

    }
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        //if not found exception will be thrown(managed in the service layer)
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO findById(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        return employeeService.employeeToDTO(employee);
    }
    @GetMapping("/employee/availability")

    /**
     * find list of employee(s) by skills and availability
     */
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek day = employeeRequestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill>skills = employeeRequestDTO.getSkills();
        List<Employee>capableEmployees = employeeService.findBySkillsAndDay(skills,day);
        List<EmployeeDTO>dtos = new ArrayList<>();

        capableEmployees.forEach(
                employee -> dtos.add(employeeService.employeeToDTO(employee))
        );
        return dtos;
    }
}
