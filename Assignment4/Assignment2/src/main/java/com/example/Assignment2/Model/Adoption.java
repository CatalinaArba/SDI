package com.example.Assignment2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.*;
import jakarta.validation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "adoption")
public class Adoption {
    private @Id
    @GeneratedValue Integer id;

    @NotNull(message ="Adoption date is mandatory")
    private LocalDate adoptionDate;
    @Min(value=1, message ="Adoption fee should be more than 0")
    private Integer adoptionFee;

    @NotBlank(message = "The adoption status is mandatory")
    private String adoptionStatus;
    @NotBlank(message = "The adoption location is mandatory")
    private String adoptionLocation;
    @NotBlank(message = "The adoption notes is mandatory")
    private String adoptionNotes;

    @OneToMany(mappedBy = "adoption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Pet> pet;

    @OneToMany(mappedBy = "adoptionAdoptionCustomer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AdoptionCustomer> adoptionCustomers;

    public Adoption(LocalDate adoptionDate, Integer adoptionFee, String adoptionStatus, String adoptionLocation, String adoptionNotes) {
        this.adoptionDate = adoptionDate;
        this.adoptionFee = adoptionFee;
        this.adoptionStatus = adoptionStatus;
        this.adoptionLocation = adoptionLocation;
        this.adoptionNotes = adoptionNotes;
    }

//    public Adoption(LocalDate adoptionDate, Integer adoptionFee, String adoptionStatus, String adoptionLocation, String adoptionNotes, List<Pet> pet) {
//        this.adoptionDate = adoptionDate;
//        this.adoptionFee = adoptionFee;
//        this.adoptionStatus = adoptionStatus;
//        this.adoptionLocation = adoptionLocation;
//        this.adoptionNotes = adoptionNotes;
//        this.pet = pet;
//    }

    public Adoption() {
    }
    public List<AdoptionCustomer> getAdoptionCustomers() {
        return adoptionCustomers;
    }

    public void setAdoptionCustomers(List<AdoptionCustomer> adoptionCustomers) {
        this.adoptionCustomers = adoptionCustomers;
    }
    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
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
