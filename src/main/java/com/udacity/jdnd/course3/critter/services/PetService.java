package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repos.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepo petRepo;

    public Pet save(Pet pet){
        return petRepo.save(pet);
    }

    public Pet findById(Long id){
        Optional<Pet> optionalPet = petRepo.findById(id);
        if(optionalPet.isPresent()){
            return optionalPet.get();
        }
        else throw new UnsupportedOperationException("no such id!");
    }

    public List<Pet> findAll() {
        return (List<Pet>) petRepo.findAll();
    }

    public List<Pet> findBycustomerId(Long id){
        return petRepo.findByCustomerId(id);
    }
}
