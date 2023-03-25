package com.example.Assignment2.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class AdoptionDTOStatisticsPetsPrice {
    private Integer id;
    private LocalDate adoptionDate;
    private Integer adoptionFee;
    private String adoptionStatus;
    private String adoptionLocation;
    private String adoptionNotes;
    private double avgPetPrice;

    public AdoptionDTOStatisticsPetsPrice() {
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

    public double getAvgPetPrice() {
        return avgPetPrice;
    }

    public void setAvgPetPrice(double avgPetPrice) {
        this.avgPetPrice = avgPetPrice;
    }
}
