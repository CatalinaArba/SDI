package com.example.Assignment2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "pet")
public class Pet {
    private @Id
    @GeneratedValue Integer id;
    private String name;
    private String petType;
    @NotNull(message="Every pet must have an age!")
    private Integer age;
    private String gender;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoption_id")
    @JsonIgnore
    Adoption adoption;

    public Pet(String name, String petType, Integer age, String gender, Integer price) {
        this.name = name;
        this.petType = petType;
        this.age = age;
        this.gender = gender;
        this.price = price;
    }

    public Pet() {
    }

    public Adoption getAdoption() {
        return adoption;
    }

    public void setAdoption(Adoption adoption) {
        this.adoption = adoption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return getId().equals(pet.getId()) && getName().equals(pet.getName()) && getPetType().equals(pet.getPetType()) && getAge().equals(pet.getAge()) && getGender().equals(pet.getGender()) && getPrice().equals(pet.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPetType(), getAge(), getGender(), getPrice());
    }

    /*
    For map function from PetController: another way but not that elegant
    public PetDTOWithId toPetDTOWithId()
    {
        PetDTOWithId pet = new PetDTOWithId();
        pet.setId(this.getId());
        pet.setAge(this.getAge());
        pet.setGender(this.getGender());
        pet.setPrice(this.getPrice());
        pet.setName(this.getName());
        pet.setPetType(this.getPetType());
        if(this.adoption==null) {
            pet.setAdoptionId(null);
        }
        else
        {
            pet.setAdoptionId(this.adoption.getId());
        }
        return pet;
    }*/
}
