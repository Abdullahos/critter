package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Transactional
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getName()==null || customerDTO.getPhoneNumber()==null) {
            throw new UnsupportedOperationException();
        }
        else {
            return customerService.customerToDTO(customerService.save(customerService.DTOToCustomer(customerDTO)));
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
}
