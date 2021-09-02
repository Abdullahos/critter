package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Pet> pet;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Employee> employee;

    private LocalDate date;

    @Type(type = "string")
    private List<EmployeeSkill> activities;

    /**
     * Although Hibernate does not require it, it is recommended to follow JavaBean conventions
     * by defining getters and setters for you entities persistent attributes.
     * You can still tell Hibernate to directly access the entity's fields.
     *
     * !!!!BUT FOR CONVERTING TO AND FROM DTO USING BeansUtil.copyProperties WE NEED GETTERS AND SETTERS!!!
     * */

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(List<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public void addEmployee(Employee employee){
        this.employee.add(employee);
    }
    public void addPet(Pet pet){
        this.pet.add(pet);
    }

}
