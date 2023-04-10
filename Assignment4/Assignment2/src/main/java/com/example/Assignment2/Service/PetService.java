package com.example.Assignment2.Service;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Model.PetDTO;
import com.example.Assignment2.Model.PetDTOWithId;
import com.example.Assignment2.Repository.IPetRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final IPetRepository petRepository;

    public PetService(IPetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Integer countAll(){
        return petRepository.findAll().size();
    }
    public List<PetDTOWithId> all(PageRequest pr) {
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.typeMap(Pet.class, PetDTOWithId.class);
        Page<Pet> petPage=petRepository.findAll(pr);
        List<PetDTOWithId> petDTOsWithIds = petPage.stream()
                .map(pet -> modelMapper.map(pet, PetDTOWithId.class))
                .collect(Collectors.toList());
        return petDTOsWithIds;

        //Same thing using map function: it works
        //return petRepository.findAll().stream().map(m->m.toPetDTOWithId()).collect(Collectors.toList());
    }
    public Pet newPet( Pet newPet) {
        return petRepository.save(newPet);
    }

    public PetDTO one( String id) {
        Integer intId=Integer.parseInt(id);
        if (petRepository.findById(intId).isEmpty())
            throw new PetNotFoundException(intId);

        ModelMapper modelMapper = new ModelMapper();
        PetDTO petDTO = modelMapper.map(petRepository.findById(intId).get(), PetDTO.class);
        return petDTO;

    }

    public Pet replacePet(Pet newPet,Integer id) {

        return petRepository.findById(id)
                .map(pet -> {
                    pet.setAge(newPet.getAge());
                    pet.setPetType(newPet.getPetType());
                    pet.setGender(newPet.getGender());
                    pet.setName(newPet.getName());
                    pet.setPrice(newPet.getPrice());
                    return petRepository.save(pet);
                })
                .orElseGet(() -> {
                    newPet.setId(id);
                    return petRepository.save(newPet);
                });
    }

    public void deletePet( Integer id) {
        petRepository.deleteById(id);
    }

    public List<Pet> byPrice( Integer minPrice) {
        return petRepository.findByPriceGreaterThanEqual(minPrice);
    }

    public List<Pet> sortByPrice() {
        List<Pet> petsList=petRepository.findAll();
        petsList.sort(Comparator.comparingInt(Pet::getPrice).reversed());
        return petsList;
    }
}
