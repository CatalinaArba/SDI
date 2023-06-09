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

    private Integer sum;

    private Integer count;

    public AdoptionDTOStatisticsPetsPrice() {
    }

    public void increaseSum(Integer sum) {
        this.sum += sum;
    }

    public void increaseCount() {
        this.count=this.count+1;
    }

    public AdoptionDTOStatisticsPetsPrice(Integer id, LocalDate adoptionDate, Integer adoptionFee, String adoptionStatus, String adoptionLocation, String adoptionNotes, Integer sum, Integer count) {
        this.id = id;
        this.adoptionDate = adoptionDate;
        this.adoptionFee = adoptionFee;
        this.adoptionStatus = adoptionStatus;
        this.adoptionLocation = adoptionLocation;
        this.adoptionNotes = adoptionNotes;
        this.sum = sum;
        this.count = count;

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

    public void computeAvgPetPrice(){
        if (count==0)
            avgPetPrice=0;
        else
            avgPetPrice= Math.round((sum/count) * 100.0) / 100.0;
    }

    public void setAvgPetPrice(double avgPetPrice) {
        this.avgPetPrice = avgPetPrice;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }


    public void setCount(Integer count) {
        this.count = count;
    }
}
