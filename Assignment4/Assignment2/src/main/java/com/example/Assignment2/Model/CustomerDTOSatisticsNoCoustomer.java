package com.example.Assignment2.Model;

public class CustomerDTOSatisticsNoCoustomer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String mail;
    private String phone;
    private Integer noCustomers;

    public CustomerDTOSatisticsNoCoustomer()
    {

    }
    public CustomerDTOSatisticsNoCoustomer(Integer id, String firstName, String lastName, String address, String mail, String phone, Integer noBooks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.noCustomers = noBooks;
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

    public Integer getNoCustomers() {
        return noCustomers;
    }

    public void setNoCustomers(Integer noCustomers) {
        this.noCustomers = noCustomers;
    }
}
