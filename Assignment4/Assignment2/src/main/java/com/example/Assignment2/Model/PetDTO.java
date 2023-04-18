package com.example.Assignment2.Model;

public class PetDTO {

    private Integer id;
    private String name;
    private String petType;
    private Integer age;
    private String gender;
    private Integer price;
    private String description;
    private AdoptionDTO adoption;

    public PetDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public AdoptionDTO getAdoption() {
        return adoption;
    }

    public void setAdoption(AdoptionDTO adoption) {
        this.adoption = adoption;
    }
}
