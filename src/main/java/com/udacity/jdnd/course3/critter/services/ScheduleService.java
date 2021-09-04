package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repos.ScheduleRepo;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    public Schedule save(Schedule schedule){
        return scheduleRepo.save(schedule);
    }

    /**
     * @return  list of Schedule objects or empty list if not any.
     */
    public List<Schedule> findAll(){
         List<Schedule> scheduleList = (List<Schedule>) scheduleRepo.findAll();
         return scheduleList;
    }

    /**
     * @param id
     * @return Schedule object or null if there's no dchedule for this id.
     */
    public Schedule findById(Long id){
        Optional<Schedule>scheduleOptional = scheduleRepo.findById(id);
        if(scheduleOptional.isPresent()){
            return scheduleOptional.get();
        }
        else return null;
    }

    /**
     * @param petId
     * @return schdeule list of given pet or empty list of no such relationship
     */
    public List<Schedule> findByPet(Long petId){
        List<Schedule> scheduleList = new ArrayList<>();
        Pet pet = petService.findById(petId);

        if(pet!=null)   scheduleList = scheduleRepo.findByPetContaining(pet);
        return scheduleList;
    }

    /***
     *
     * @param employeeId
     * @return schdeule list of given employee or empty list of no such relationship
     */
    public List<Schedule> findByEmployee(Long employeeId){
        Employee employee = employeeService.findById(employeeId);
        List<Schedule> scheduleListContainingEmployee = new ArrayList<>();
        if(employee!=null){
            scheduleListContainingEmployee = scheduleRepo.findByEmployeeContaining(employee);
        }
        return scheduleListContainingEmployee;
    }

    /**
     *
     * @param customerId
     * @return  schdeule list of given customer or empty list of no such relationship
     */
    public List<Schedule> findByCustomer(long customerId) {
        List<Pet> petsOwnedByCustomer = petService.findBycustomerId(customerId);
        List<Schedule> schedules = new ArrayList<>();
        if(!petsOwnedByCustomer.isEmpty()) {
            petsOwnedByCustomer.forEach(pet -> {
                scheduleRepo.findByPetContaining(pet).forEach(pet2 -> schedules.add(pet2));
            });
        }
        return schedules;
    }

    /**
     * @param schedule
     * @return  ScheduleDTO
     */
    public ScheduleDTO toDTO(Schedule schedule){
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,dto);
        /***/
        List<Employee>emps = schedule.getEmployee();
        List<Pet>pets = schedule.getPet();

        emps.forEach(employee -> dto.addEmployeeId(employee.getId()));
        pets.forEach(pet -> dto.addPetId(pet.getId()));

        return dto;
    }

    /**
     * @param dto
     * @return Schedule
     */
    public Schedule toSchedule(ScheduleDTO dto){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto,schedule);

        List<Long>petsIds = dto.getPetIds();
        List<Long>empsIds = dto.getEmployeeIds();

        petsIds.forEach(id-> schedule.addPet(petService.findById(id)));
        empsIds.forEach(id->schedule.addEmployee(employeeService.findById(id)));

        return schedule;
    }
}
