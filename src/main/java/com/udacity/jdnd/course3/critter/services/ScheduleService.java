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
import java.util.InputMismatchException;
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

    public ScheduleDTO save(ScheduleDTO scheduleDTO){
        return toDTO(scheduleRepo.save(toSchedule(scheduleDTO)));
    }
    public List<ScheduleDTO> findAll(){
         List<Schedule> scheduleList = (List<Schedule>) scheduleRepo.findAll();
         List<ScheduleDTO>scheduleDTOS = new ArrayList<>();
         scheduleList.forEach(schedule->scheduleDTOS.add(toDTO(schedule)));
         return scheduleDTOS;
    }

    public ScheduleDTO findById(Long id){
        Optional<Schedule>scheduleOptional = scheduleRepo.findById(id);
        if(scheduleOptional.isPresent()){
            return toDTO(scheduleOptional.get());
        }
        else throw new InputMismatchException("no such schedule id!");
    }
    public List<ScheduleDTO> findByPet(Long petId){
        Pet pet = petService.findById(petId);
        List<Schedule> scheduleList = scheduleRepo.findByPetContaining(pet);
        List<ScheduleDTO> dtos = new ArrayList<>();

        scheduleList.forEach(schedule -> dtos.add(toDTO(schedule)));
        return dtos;
    }

    public List<ScheduleDTO> findByEmployee(Long employeeId){
        Employee employee = employeeService.findById(employeeId);
        List<Schedule> scheduleListContainingEmployee = scheduleRepo.findByEmployeeContaining(employee);
        List<ScheduleDTO>dtos = new ArrayList<>();

        scheduleListContainingEmployee.forEach(schedule -> dtos.add(toDTO(schedule)));
        return dtos;
    }

    /**
     * DTO conversion
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
    public Schedule toSchedule(ScheduleDTO dto){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto,schedule);

        List<Long>petsIds = dto.getPetIds();
        List<Long>empsIds = dto.getEmployeeIds();

        petsIds.forEach(id-> schedule.addPet(petService.findById(id)));
        empsIds.forEach(id->schedule.addEmployee(employeeService.findById(id)));

        return schedule;
    }

    public List<ScheduleDTO> findByCustomer(long customerId) {
        List<ScheduleDTO>schedules = new ArrayList<>();
        List<Pet> petsOwnedByCustomer = petService.findBycustomerId(customerId);
        petsOwnedByCustomer.forEach(pet-> {
            scheduleRepo.findByPetContaining(pet).forEach(pet2 -> schedules.add(toDTO(pet2)));
        });
        return schedules;
    }
}
