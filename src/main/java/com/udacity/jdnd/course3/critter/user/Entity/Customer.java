package com.udacity.jdnd.course3.critter.user.Entity;


import com.udacity.jdnd.course3.critter.pet.Pet;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "customer")   //on customer can have many pets, FETCH.LAZY (by default) to avoid recursive cascading.
    private List<Pet> petList;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    /**
     * add to petList and then hibernate will map that to db(hibernate :U work with entities, hibernate jpa make db changes)
     */
    public void addPetToList(Pet pet){
        petList.add(pet);
    }


}
