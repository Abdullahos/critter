package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@Embeddable
public class ScheduleDTO {
    private long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    /***/
    public void addEmployeeId(Long id){
        if(employeeIds==null){
            employeeIds = new ArrayList<>();
        }
        this.employeeIds.add(id);
    }

    public void addPetId(Long id){
        if (petIds == null) {
            this.petIds = new ArrayList<>();
        }
        this.petIds.add(id);
    }
    public void addActivity(EmployeeSkill skills){
        if(this.activities==null){
            this.activities = (Set<EmployeeSkill>) new ArrayList<EmployeeSkill>();
        }
        activities.add(skills);
    }
}
