package com.example.Assignment2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "AdoptionCustomer")
@JsonIgnoreProperties("adoptionAdoptionCustomer")
public class AdoptionCustomer {
    private @Id
    @GeneratedValue Integer id;
    private String adoptionContract;
    private String customerFeedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoption_id")
    @JsonIgnore
    Adoption adoptionAdoptionCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    Customer customerAdoptionCustomer;

    public AdoptionCustomer() {
    }
    public AdoptionCustomer(Adoption adoptionAdoptionCustomer, Customer customerAdoptionCustomer, String adoptionContract, String customerFeedback) {
        this.adoptionAdoptionCustomer = adoptionAdoptionCustomer;
        this.customerAdoptionCustomer = customerAdoptionCustomer;
        this.adoptionContract = adoptionContract;
        this.customerFeedback = customerFeedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Adoption getAdoptionAdoptionCustomer() {
        return adoptionAdoptionCustomer;
    }

    public void setAdoptionAdoptionCustomer(Adoption adoption) {
        this.adoptionAdoptionCustomer = adoption;
    }

    public Customer getCustomerAdoptionCustomer() {
        return customerAdoptionCustomer;
    }

    public void setCustomerAdoptionCustomer(Customer customer) {
        this.customerAdoptionCustomer = customer;
    }

    public String getAdoptionContract() {
        return adoptionContract;
    }

    public void setAdoptionContract(String adoptionContract) {
        this.adoptionContract = adoptionContract;
    }

    public String getCustomerFeedback() {
        return customerFeedback;
    }

    public void setCustomerFeedback(String customerFeedback) {
        this.customerFeedback = customerFeedback;
    }
}
