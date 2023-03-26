package com.example.Assignment2.Model;

import java.util.List;

public class AdoptionDTOWithCustomerIds {
    Adoption adoption;
    List<Integer> customersIds;

    public AdoptionDTOWithCustomerIds() {
    }

    public Adoption getAdoption() {
        return adoption;
    }

    public void setAdoption(Adoption adoption) {
        this.adoption = adoption;
    }

    public List<Integer> getCustomersIds() {
        return customersIds;
    }

    public void setCustomersIds(List<Integer> customersIds) {
        this.customersIds = customersIds;
    }
}
