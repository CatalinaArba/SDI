package com.example.Assignment2.Controller;

import com.example.Assignment2.Exception.AdoptionNotFoundException;
import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdoptionController {
    @Autowired
    private final IAdoptionRepository adoptionRepository;

    private final IPetRepository petRepository;

    public AdoptionController(IAdoptionRepository adoptionRepository, IPetRepository petRepository) {
        this.adoptionRepository = adoptionRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/adoptions")
    List<AdoptionDTO> all() {
        ModelMapper modelMapper = new ModelMapper();
        List<Adoption> adoptions = adoptionRepository.findAll();
        List<AdoptionDTO> adoptionDTOs = adoptions.stream()
                .map(adoption -> modelMapper.map(adoption, AdoptionDTO.class))
                .collect(Collectors.toList());
        return adoptionDTOs;
    }


    @PostMapping("/adoptions/{petId}")
    Adoption newAdoption(@RequestBody Adoption newAdoption,@PathVariable Integer petId) {
        Pet pet=petRepository.findById(petId).get();
        List<Pet> petList=new ArrayList<>();
        petList.add(pet);
        newAdoption.setPet(petList);
        //newAdoption.getAdoptionCustomers().isEmpty();
        newAdoption=adoptionRepository.save(newAdoption);
        pet.setAdoption(newAdoption);
        return newAdoption;
    }

    /*@PostMapping("/adoptions/{adoptionId}/pets")
    Pet newPetForAdoption(@PathVariable Integer adoptionId, @RequestBody Pet newPet) {
        // Find the adoption by ID
        Adoption adoption = adoptionRepository.findById(adoptionId)
                .orElseThrow(() -> new AdoptionNotFoundException(adoptionId));

        // Check if the pet with the given ID already exists
        Pet existingPet = null;
        for (Pet pet : petRepository.findAll()) {
            if (pet.equals(newPet)) {
                existingPet = pet;
                break;
            }
        }
        if (existingPet != null) {
            // If the pet already exists, set the adoption property and return the existing pet
            existingPet.setAdoption(adoption);
            petRepository.save(existingPet);
            return existingPet;
        } else {
            // If the pet does not exist, set the adoption property for the new pet
            newPet.setAdoption(adoption);
            // Save the new pet to the database
            Pet savedPet = petRepository.save(newPet);
            // Add the new pet to the adoption
            adoption.getPet().add(savedPet);
            // Save the adoption to update the pets list
            adoptionRepository.save(adoption);
            return savedPet;
        }
    }*/

    // Single item
    @GetMapping("/adoptions/{id}")
    Adoption one(@PathVariable Integer id) {
        if (adoptionRepository.findById(id).isEmpty())
            throw new PetNotFoundException(id);

        Adoption adoption = adoptionRepository.findById(id).get();
        return adoption;
    }

    @PutMapping("/adoptions/{id}")
    Adoption replaceAdoption(@RequestBody Adoption newAdoption, @PathVariable Integer id) {

        Adoption oldAdoption=adoptionRepository.findById(id).get();
        return adoptionRepository.findById(id)
                .map(adoption -> {
                    adoption.setAdoptionDate(newAdoption.getAdoptionDate());
                    adoption.setAdoptionFee(newAdoption.getAdoptionFee());
                    adoption.setAdoptionLocation(newAdoption.getAdoptionLocation());
                    adoption.setAdoptionNotes(newAdoption.getAdoptionNotes());
                    adoption.setAdoptionStatus(newAdoption.getAdoptionStatus());
                    adoption.setPet(oldAdoption.getPet());
                    adoption.setAdoptionCustomers(oldAdoption.getAdoptionCustomers());
                    return adoptionRepository.save(adoption);
                })
                .orElseGet(() -> {
                    newAdoption.setId(id);
                    return adoptionRepository.save(newAdoption);
                });
    }

    @DeleteMapping("/adoptions/{id}")
    void deleteAdoption(@PathVariable Integer id) {
        adoptionRepository.deleteById(id);
    }

    //all the adoptions ordered by the average pet's price
    @GetMapping("/adoptions/statistics")
    public List<AdoptionDTOStatisticsPetsPrice> getAllAdoptionsOrderByAvgPetPrice() {
        List<Adoption> adoptions = adoptionRepository.findAll();
        List<AdoptionDTOStatisticsPetsPrice> adoptionDTOStatisticsPetsPrices = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Adoption adoption : adoptions) {
            double sum = 0.0;
            int count = 0;
            for (Pet pet : adoption.getPet()) {
                sum += pet.getPrice();
                count++;
            }
            double avgPrice = count > 0 ? sum / count : 0.0;
            avgPrice=Math.round(avgPrice * 100.0) / 100.0;
            AdoptionDTOStatisticsPetsPrice adoptionDTOStatisticsPetsPrice = modelMapper.map(adoption, AdoptionDTOStatisticsPetsPrice.class);
            adoptionDTOStatisticsPetsPrice.setAvgPetPrice(avgPrice);
            adoptionDTOStatisticsPetsPrices.add(adoptionDTOStatisticsPetsPrice);
        }
        adoptionDTOStatisticsPetsPrices.sort(Comparator.comparingDouble(AdoptionDTOStatisticsPetsPrice::getAvgPetPrice).reversed());
        return adoptionDTOStatisticsPetsPrices;
    }
}