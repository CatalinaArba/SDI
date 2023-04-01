package com.example.Assignment2.Model;

public class AdoptionCustomerDTOWithId {
    private Integer id;
    private String adoptionContract;
    private String customerFeedback;
    private Integer idAdoptionAdoptionCustomer;
    private Integer idCustomerAdoptionCustomer;

    public AdoptionCustomerDTOWithId() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getIdAdoptionAdoptionCustomer() {
        return idAdoptionAdoptionCustomer;
    }

    public void setIdAdoptionAdoptionCustomer(Integer idAdoptionAdoptionCustomer) {
        this.idAdoptionAdoptionCustomer = idAdoptionAdoptionCustomer;
    }

    public Integer getIdCustomerAdoptionCustomer() {
        return idCustomerAdoptionCustomer;
    }

    public void setIdCustomerAdoptionCustomer(Integer idCustomerAdoptionCustomer) {
        this.idCustomerAdoptionCustomer = idCustomerAdoptionCustomer;
    }
}
