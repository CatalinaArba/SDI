package com.example.Assignment2.Service;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdoptionService {
    @Autowired
    private final IAdoptionRepository adoptionRepository;

    private IAdoptionCustomerRepository adoptionCustomerRepository;
    private final IPetRepository petRepository;

    public AdoptionService(IAdoptionRepository adoptionRepository, IPetRepository petRepository,IAdoptionCustomerRepository adoptionCustomerRepository) {
        this.adoptionCustomerRepository=adoptionCustomerRepository;
        this.adoptionRepository = adoptionRepository;
        this.petRepository = petRepository;
    }


    public List<AdoptionDTO> all(PageRequest pr) {
        ModelMapper modelMapper = new ModelMapper();
        Page<Adoption> adoptions = adoptionRepository.findAll(pr);
        List<AdoptionDTO> adoptionDTOs = adoptions.stream()
                .map(adoption -> modelMapper.map(adoption, AdoptionDTO.class))
                .collect(Collectors.toList());
        return adoptionDTOs;
    }

    public Long countAll(){
        return adoptionRepository.count();
    }


    public Adoption newAdoption(Adoption newAdoption)
    {
        return adoptionRepository.save(newAdoption);
    }

    public Adoption newAdoptionWithPetId( Adoption newAdoption, Integer petId) {
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


    public AdoptionDTOWithCustomerIds one( String idString) {
        Integer id=Integer.parseInt(idString);
        if (adoptionRepository.findById(id).isEmpty())
            throw new PetNotFoundException(id);

        Adoption adoption=adoptionRepository.findById(id).get();
        AdoptionDTOWithCustomerIds adoptionDTOWithCustomerIds=new AdoptionDTOWithCustomerIds();

        List<Integer> customersIds=new ArrayList<>();

        List<AdoptionCustomer> adoptionCustomers=adoptionCustomerRepository.findAdoptionCustomerByAdoptionAdoptionCustomerId(adoption.getId());
        for(AdoptionCustomer ac:adoptionCustomers) {
            if (ac.getAdoptionAdoptionCustomer().getId() == adoption.getId()) {
                customersIds.add(ac.getCustomerAdoptionCustomer().getId());
            }
        }

        adoptionDTOWithCustomerIds.setCustomersIds(customersIds);
        adoptionDTOWithCustomerIds.setAdoption(adoption);
        return  adoptionDTOWithCustomerIds;
    }

    public Adoption replaceAdoption( Adoption newAdoption,  Integer id) {

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


    public void deleteAdoption( Integer id) {
        adoptionRepository.deleteById(id);
    }

    //all the adoptions ordered by the average pet's price

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

    public List<Adoption> getAdoptionIdsAutocomplete( String query)
    {

        List<Adoption> adoptions=adoptionRepository.findAll();

        return adoptions.stream()
                .filter(adoption -> adoption.getId().toString().startsWith(query)).limit(20)
                .collect(Collectors.toList());
    }
}
