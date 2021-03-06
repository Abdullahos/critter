package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.exception.MissingInfoException;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFound;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repos.PetRepo;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private PetRepo petRepo;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

       if(petDTO.getOwnerId()==null || petDTO.getName()== null || petDTO.getType()==null)    throw new MissingInfoException("owner id, pet name, or type can't be null!");
       else {
            PetDTO petDTO1 = petToDTO(petService.save(petDTOToPet(petDTO)));
            Customer customer = customerService.findById(petDTO1.getOwnerId());
            customer.addPetToList(petDTOToPet(petDTO1));
            customerService.save(customer);
            return petDTO1;
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        if(pet==null)   throw new ObjectNotFound("no such pet id!");
        return petToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList =  petService.findAll();
        List<PetDTO>petDTOS = new ArrayList<>();
        petList.forEach(pet -> petDTOS.add(petToDTO(pet)));
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO>petDTOS = new ArrayList<>();
        List<Pet> petList = petRepo.findByCustomerId(ownerId);
        if(!petList.isEmpty()){
            petList.forEach(pet -> petDTOS.add(petToDTO(pet)));
            return petDTOS;
        }
        throw new ObjectNotFound("no such owner id!");
    }

    public PetDTO petToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        if(pet.getCustomer()!=null){
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    public Pet petDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        if(petDTO.getOwnerId()!=null){
            pet.setCustomer(customerService.findById(petDTO.getOwnerId()));
        }
        return pet;
    }
}
