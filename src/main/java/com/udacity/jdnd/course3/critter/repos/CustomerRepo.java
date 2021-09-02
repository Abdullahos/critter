package com.udacity.jdnd.course3.critter.repos;

import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends CrudRepository<Customer,Long> {
    Customer findByPetListId(Long id);
}
