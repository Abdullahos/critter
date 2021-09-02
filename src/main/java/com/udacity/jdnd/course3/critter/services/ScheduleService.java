package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DAO.ScheduleDAO;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repos.SchduleDAOImpl;
import com.udacity.jdnd.course3.critter.repos.ScheduleRepo;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private SchduleDAOImpl findByEmployeeId;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    public Schedule save(ScheduleDTO scheduleDTO){
        return scheduleRepo.save(toSchedule(scheduleDTO));
    }
    public List<Schedule> findAll(){
        return (List<Schedule>) scheduleRepo.findAll();
    }

    public Schedule findById(Long id){
        Optional<Schedule>scheduleOptional = scheduleRepo.findById(id);
        if(scheduleOptional.isPresent()){
            return scheduleOptional.get();
        }
        else throw new InputMismatchException("no such schedule id!");
    }
   /* public List<Schedule> findByPet(Long petId){
        return scheduleRepo.findByPetId(petId);
    }
    */
    public List<ScheduleDTO> findByEmployee(Long employeeId){
        return findByEmployeeId.findByEmployeeId(employeeId);
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
}
