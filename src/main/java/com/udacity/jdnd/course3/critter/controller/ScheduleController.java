package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.exception.ObjectNotFound;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.toDTO(scheduleService.save(scheduleService.toSchedule(scheduleDTO)));
    }
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule>scheduleList = scheduleService.findAll();
        List<ScheduleDTO>scheduleDTOS = new ArrayList<>();
        if(scheduleList!=null){
            scheduleList.forEach(schedule -> scheduleDTOS.add(scheduleService.toDTO(schedule)));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule>scheduleList = scheduleService.findByPet(petId);
        //if there's schedule attached to this pet
        if(!scheduleList.isEmpty()){
            List<ScheduleDTO>scheduleDTOS = new ArrayList<>();
            scheduleList.forEach(schedule -> scheduleDTOS.add(scheduleService.toDTO(schedule)));
            return scheduleDTOS;
        }
        else throw new ObjectNotFound("no such schedule of this pet id!");
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule>scheduleList = scheduleService.findByEmployee(employeeId);
        if(!scheduleList.isEmpty()){
            List<ScheduleDTO>scheduleDTOS = new ArrayList<>();
            scheduleList.forEach(schedule -> scheduleDTOS.add(scheduleService.toDTO(schedule)));
            return scheduleDTOS;
        }
        else {
            throw new ObjectNotFound("no such schedule for that employee!");
        }
    }
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule>scheduleList =  scheduleService.findByCustomer(customerId);
        List<ScheduleDTO>scheduleDTOS = new ArrayList<>();
        if(!scheduleList.isEmpty()){
            scheduleList.forEach(schedule -> scheduleDTOS.add(scheduleService.toDTO(schedule)));
            return scheduleDTOS;
        }
        else {
            throw new ObjectNotFound("no such schedule for that customer");
        }
    }
}
