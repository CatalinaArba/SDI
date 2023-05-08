package com.example.Assignment2.Controller;

import com.example.Assignment2.Exception.AdoptionNotFoundException;
import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import com.example.Assignment2.Service.AdoptionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Destination;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AdoptionController {

    @Autowired
    private final AdoptionService adoptionService;


    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping("/adoptions/page/{page}/size/{size}")
    List<AdoptionDTO> all(@PathVariable int page, @PathVariable int size) {
        PageRequest pr=PageRequest.of(page,size);
        return adoptionService.all(pr);
    }

    @GetMapping("/adoptions/count")
    Long countAll() {
        return adoptionService.countAll();
    }

    @PostMapping("/adoptions/{petId}")
    Adoption newAdoption(@Valid @RequestBody Adoption newAdoption, @PathVariable Integer petId) {
        return adoptionService.newAdoptionWithPetId(newAdoption,petId);
    }

    @PostMapping("/adoptions")
    Adoption newAdoption(@Valid @RequestBody Adoption newAdoption) {
        return adoptionService.newAdoption(newAdoption);
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

    @GetMapping("/adoptions/{id}")
    Adoption one(@PathVariable String id) {
        return adoptionService.one(id);
    }

    @PutMapping("/adoptions/{id}")
    Adoption replaceAdoption(@RequestBody Adoption newAdoption, @PathVariable Integer id) {

        return adoptionService.replaceAdoption(newAdoption,id);
    }

    @DeleteMapping("/adoptions/{id}")
    void deleteAdoption(@PathVariable String id) {
        Integer intID=Integer.parseInt(id);
        adoptionService.deleteAdoption(intID);
    }

    //all the adoptions ordered by the average pet's price
    @GetMapping("/adoptions/avgPetPrice/page/{page}/size/{size}")
    public List<AdoptionDTOStatisticsPetsPrice> getAllAdoptionsOrderByAvgPetPrice(@PathVariable int page, @PathVariable int size) {
        return adoptionService.getAllAdoptionsOrderByAvgPetPrice(page,size);
    }

    @GetMapping("/adoptions/customers/page/{page}/size/{size}")
    public List<AdoptionDTOStatisticsCustomersNo> getAllAdoptionsAndCustomers(@PathVariable int page, @PathVariable int size) {
        return adoptionService.getAllAdoptionsAnnTheCustomersNo(page,size);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName =((FieldError) error).getField();
            String errorMessage =error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/adoptions/autocomplete")
    public List<Adoption> getAdoptionsSuggestions(@RequestParam String query)
    {
        return this.adoptionService.getAdoptionIdsAutocomplete(query);
    }
}