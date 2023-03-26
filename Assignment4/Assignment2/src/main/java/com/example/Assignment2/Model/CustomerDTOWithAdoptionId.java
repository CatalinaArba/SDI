package com.example.Assignment2.Model;

import java.util.List;

public class CustomerDTOWithAdoptionId {
    Customer customer;

    private List<Integer> adoptionIds;

    public CustomerDTOWithAdoptionId() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Integer> getAdoptionIds() {
        return adoptionIds;
    }

    public void setAdoptionIds(List<Integer> adoptionIds) {
        this.adoptionIds = adoptionIds;
    }


}
