package com.example.Assignment2.Model;

public class AdoptionCustomerDTO {

    private Integer id;

    private String adoptionContract;
    private String customerFeedback;
    AdoptionDTO adoptionAdoptionCustomer;
    CustomerDTO customerAdoptionCustomer;




    public AdoptionCustomerDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdoptionDTO getAdoptionAdoptionCustomer() {
        return adoptionAdoptionCustomer;
    }

    public void setAdoptionAdoptionCustomer(AdoptionDTO adoptionAdoptionCustomer) {
        this.adoptionAdoptionCustomer = adoptionAdoptionCustomer;
    }

    public CustomerDTO getCustomerAdoptionCustomer() {
        return customerAdoptionCustomer;
    }

    public void setCustomerAdoptionCustomer(CustomerDTO customerAdoptionCustomer) {
        this.customerAdoptionCustomer = customerAdoptionCustomer;
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
