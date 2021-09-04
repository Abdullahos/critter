package com.udacity.jdnd.course3.critter.user.Entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Transactional
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    /**ElementCollection create table for skills(not entity) and add employee id column to it.
     * FetchType.EAGER to retrieve the skills with getting employee info without specific ask for it.
     */
    @ElementCollection(targetClass = EmployeeSkill.class,fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    /**ElementCollection create table for daysAvailable(not entity) and add employee id column to it.
     * FetchType.EAGER to retrieve the daysAvailable with getting employee info without specific ask for it.
     */
    @ElementCollection(targetClass = DayOfWeek.class,fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
