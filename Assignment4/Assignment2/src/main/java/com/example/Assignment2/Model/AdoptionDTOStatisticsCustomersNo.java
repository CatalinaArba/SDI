package com.example.Assignment2.Model;

import java.time.LocalDate;

public class AdoptionDTOStatisticsCustomersNo {
    private Integer id;
    private LocalDate adoptionDate;
    private Integer adoptionFee;
    private String adoptionStatus;
    private String adoptionLocation;
    private String adoptionNotes;
    private Integer customerNumbers;

    public AdoptionDTOStatisticsCustomersNo() {
    }

    public AdoptionDTOStatisticsCustomersNo(Integer id, LocalDate adoptionDate, Integer adoptionFee, String adoptionStatus, String adoptionLocation, String adoptionNotes, Integer customerNumbers) {
        this.id = id;
        this.adoptionDate = adoptionDate;
        this.adoptionFee = adoptionFee;
        this.adoptionStatus = adoptionStatus;
        this.adoptionLocation = adoptionLocation;
        this.adoptionNotes = adoptionNotes;
        this.customerNumbers = customerNumbers;
    }

    public void increaseNo(){
        this.customerNumbers+=1;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public Integer getAdoptionFee() {
        return adoptionFee;
    }

    public void setAdoptionFee(Integer adoptionFee) {
        this.adoptionFee = adoptionFee;
    }

    public String getAdoptionStatus() {
        return adoptionStatus;
    }

    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    public String getAdoptionLocation() {
        return adoptionLocation;
    }

    public void setAdoptionLocation(String adoptionLocation) {
        this.adoptionLocation = adoptionLocation;
    }

    public String getAdoptionNotes() {
        return adoptionNotes;
    }

    public void setAdoptionNotes(String adoptionNotes) {
        this.adoptionNotes = adoptionNotes;
    }

    public Integer getCustomerNumbers() {
        return customerNumbers;
    }

    public void setCustomerNumbers(Integer customerNumbers) {
        this.customerNumbers = customerNumbers;
    }
}
