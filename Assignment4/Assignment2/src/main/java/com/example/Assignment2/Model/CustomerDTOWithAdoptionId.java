package com.example.Assignment2.Model;

import java.util.List;

public class CustomerDTOWithAdoptionId {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String mail;
    private String phone;

    private List<Integer> adoptionIds;

    public CustomerDTOWithAdoptionId() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getAdoptionIds() {
        return adoptionIds;
    }

    public void setAdoptionIds(List<Integer> adoptionIds) {
        this.adoptionIds = adoptionIds;
    }


}
