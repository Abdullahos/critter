package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY) /******pet to be to come many times(different days or we 'll need to remove each schedule record when it done) *****/
    private List<Pet> pet;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Employee> employee;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class,fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

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

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public void addEmployee(Employee employee){
        if(this.employee==null){
            this.employee = new ArrayList<>();
        }
        this.employee.add(employee);
    }
    public void addPet(Pet pet){
        if (this.pet==null){
            this.pet = new ArrayList<>();
        }
        this.pet.add(pet);
    }

}
