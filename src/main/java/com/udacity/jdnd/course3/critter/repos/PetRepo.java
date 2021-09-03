package com.udacity.jdnd.course3.critter.repos;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepo extends CrudRepository<Pet,Long> {
    List<Pet> findByCustomerId(Long id);
}

