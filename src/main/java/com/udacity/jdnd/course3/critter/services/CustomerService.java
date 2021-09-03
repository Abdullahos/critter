package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repos.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PetService petService;

    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer findById(Long ownerId){
        Optional<Customer>customerOptional =  customerRepo.findById(ownerId);
        if(customerOptional.isPresent())    return customerOptional.get();
        else return null;/**exception handling*/
    }

    public List<Customer> findAll() {
        return (List<Customer>) customerRepo.findAll();
    }
    public Customer findByPetListId(Long id){
        return customerRepo.findByPetListId(id);
    }

    /**this method convert to DTO**/
    public CustomerDTO customerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPetIds(new ArrayList<>());
        List<Long>petsIds = new ArrayList<>();

        BeanUtils.copyProperties(customer,customerDTO);
        //does that customer.id is referenced in any pet table?
        if(customer.getPetList()!=null){
            customer.getPetList().forEach(pet -> petsIds.add(pet.getId()));
            customerDTO.setPetIds(petsIds);
        }
        return customerDTO;
    }
    public Customer DTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setPetList(new ArrayList<>());
        List<Pet>petsList = new ArrayList<>();

        BeanUtils.copyProperties(customerDTO,customer);

        if(customerDTO.getPetIds() != null){
            customerDTO.getPetIds().forEach(id->petsList.add(petService.findById(id)));
            customer.setPetList(petsList);
        }

        return customer;
    }
}

