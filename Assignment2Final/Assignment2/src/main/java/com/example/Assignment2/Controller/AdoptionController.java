package com.example.Assignment2.Controller;

import com.example.Assignment2.Exception.AdoptionNotFoundException;
import com.example.Assignment2.Model.Adoption;
import com.example.Assignment2.Model.AdoptionDTO;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Destination;
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


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/adoptions")
    List<AdoptionDTO> all() {
        ModelMapper modelMapper = new ModelMapper();
        List<Adoption> adoptions = adoptionRepository.findAll();
        List<AdoptionDTO> adoptionDTOs = adoptions.stream()
                .map(adoption -> modelMapper.map(adoption, AdoptionDTO.class))
                .collect(Collectors.toList());
        return adoptionDTOs;
    }
    // end::get-aggregate-root[]


    @PostMapping("/adoptions")
    Adoption newAdoption(@RequestBody Adoption newAdoption) {
        return adoptionRepository.save(newAdoption);
    }

    @PostMapping("/adoptions/{adoptionId}/pets")
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
    }

    // Single item
    @GetMapping("/adoptions/{id}")
    Adoption one(@PathVariable Integer id) {

        return adoptionRepository.findById(id)
                .orElseThrow(() -> new AdoptionNotFoundException(id));
    }

    @PutMapping("/adoptions/{id}")
    Adoption replaceAdoption(@RequestBody Adoption newAdoption, @PathVariable Integer id) {

        return adoptionRepository.findById(id)
                .map(adoption -> {
                    adoption.setAdoptionDate(newAdoption.getAdoptionDate());
                    adoption.setAdoptionFee(newAdoption.getAdoptionFee());
                    adoption.setAdoptionLocation(newAdoption.getAdoptionLocation());
                    adoption.setAdoptionNotes(newAdoption.getAdoptionNotes());
                    adoption.setAdoptionStatus(newAdoption.getAdoptionStatus());
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
}