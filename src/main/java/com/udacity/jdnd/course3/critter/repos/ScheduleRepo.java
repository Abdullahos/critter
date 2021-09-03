package com.udacity.jdnd.course3.critter.repos;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends CrudRepository<Schedule,Long> {
    /**org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [com.udacity.jdnd.course3.critter.schedule.Schedule] to type [com.udacity.jdnd.course3.critter.schedule.ScheduleDTO]
     * WILL BE THROWN IF WE MAKE THE RETURN TYPE OF "findByPetContaining" FOR EXAMPLE IS SOMTHING CAN'T BE CAST AUTOMATICALLY LIKE ScheduleDTO
     * */
    List<Schedule> findByPetContaining(Pet pet);
    List<Schedule> findByEmployeeContaining(Employee employee);

}
