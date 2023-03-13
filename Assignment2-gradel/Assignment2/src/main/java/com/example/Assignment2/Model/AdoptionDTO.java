package com.example.Assignment2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AdoptionDTO {
    private Integer id;
    private LocalDate adoptionDate;
    private Integer adoptionFee;
    private String adoptionStatus;
    private String adoptionLocation;
    private String adoptionNotes;

    public AdoptionDTO(LocalDate adoptionDate,  Integer adoptionFee, String adoptionStatus, String adoptionLocation, String adoptionNotes) {//Integer petId, Integer customerId,
        this.adoptionDate = adoptionDate;
        this.adoptionFee = adoptionFee;
        this.adoptionStatus = adoptionStatus;
        this.adoptionLocation = adoptionLocation;
        this.adoptionNotes = adoptionNotes;
    }


    public AdoptionDTO() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adoption adoption)) return false;
        return getId().equals(adoption.getId()) && getAdoptionDate().equals(adoption.getAdoptionDate()) &&  getAdoptionFee().equals(adoption.getAdoptionFee()) && getAdoptionStatus().equals(adoption.getAdoptionStatus()) && getAdoptionLocation().equals(adoption.getAdoptionLocation()) && getAdoptionNotes().equals(adoption.getAdoptionNotes());//getPetId().equals(adoption.getPetId()) && getCustomerId().equals(adoption.getCustomerId()) &&
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAdoptionDate(), getAdoptionFee(), getAdoptionStatus(), getAdoptionLocation(), getAdoptionNotes());//, getPetId(), getCustomerId(),
    }



}
