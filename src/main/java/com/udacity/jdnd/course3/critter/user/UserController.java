package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.aspectj.apache.bcel.generic.LineNumberGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@Transactional
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getName()==null || customerDTO.getPhoneNumber()==null) {
            throw new UnsupportedOperationException();
        }
        else {
            return customerService.save(customerDTO);
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.findAll();
        List<CustomerDTO>customerDTOS = new ArrayList<>();

        if(customerList!=null) {
            customerList.forEach(customer -> customerDTOS.add(customerService.customerToDTO(customer)));
        }
        return customerDTOS;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.findByPetListId(petId);
        if(customer!=null)  return customerService.customerToDTO(customer);
        throw new UnsupportedOperationException("no such pet id!");
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        //some dummies assumption, can be configured when understand the requirements more clearly
        if (employeeDTO.getName() == null || employeeDTO.getSkills() == null) throw new UnsupportedOperationException();
        else {
            Employee employee = employeeService.DTOToEmployee(employeeDTO);
            EmployeeDTO retrievedEmployee = employeeService.save(employee);
            return retrievedEmployee;
        }
    }
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee retrievedEmployee =  employeeService.findById(employeeId);
        EmployeeDTO dto = employeeService.employeeToDTO(retrievedEmployee);
        return dto;
        // throw new UnsupportedOperationException();

    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        //if not found exception will be thrown(managed in the service layer)
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
       // throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO findById(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        return employeeService.employeeToDTO(employee);
    }
    @GetMapping("/employee/availability")
    /***
     * find list of employee(s) by skills and availability
     */
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek day = employeeRequestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill>skills = employeeRequestDTO.getSkills();
        List<EmployeeDTO>capableEmployees = employeeService.findBySkillsAndDay(skills,day);
       return capableEmployees;
    }

}
