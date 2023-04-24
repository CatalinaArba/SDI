package com.example.Assignment2.Service;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.Adoption;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Model.PetDTO;
import com.example.Assignment2.Model.PetDTOWithId;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
    private final IAdoptionRepository adoptionRepository;

    public PetService(IPetRepository petRepository,IAdoptionRepository adoptionRepository) {
        this.petRepository = petRepository;
        this.adoptionRepository=adoptionRepository;
    }

    public Long countAll(){
        return petRepository.count();
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
    public Pet newPet( PetDTOWithId newPet) {
        Adoption adoption=adoptionRepository.findById(newPet.getAdoptionId()).get();
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<PetDTOWithId, Pet> typeMap = modelMapper.createTypeMap(PetDTOWithId.class, Pet.class);
//        typeMap.addMappings(mapping -> {
//            mapping.map(PetDTOWithId::getName, Pet::setName);
//            mapping.map(PetDTOWithId::getAge, Pet::setAge);
//            mapping.map(PetDTOWithId::getPetType, Pet::setPetType);
//            mapping.map(PetDTOWithId::getDescription, Pet::setDescription);
//            mapping.map(PetDTOWithId::getGender, Pet::setGender);
//            mapping.map(PetDTOWithId::getPrice, Pet::setPrice);
//        });
        Pet pet = modelMapper.map(newPet, Pet.class);
        pet.setAdoption(adoption);

        return petRepository.save(pet);
    }

    public PetDTO one( String id) {
        Integer intId=Integer.parseInt(id);
        if (petRepository.findById(intId).isEmpty())
            throw new PetNotFoundException(intId);

        ModelMapper modelMapper = new ModelMapper();
        PetDTO petDTO = modelMapper.map(petRepository.findById(intId).get(), PetDTO.class);
        return petDTO;

    }

    public Pet replacePet(PetDTOWithId newPet,Integer id) {
        Adoption adoption=adoptionRepository.findById(newPet.getAdoptionId()).get();
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setAge(newPet.getAge());
                    pet.setPetType(newPet.getPetType());
                    pet.setGender(newPet.getGender());
                    pet.setName(newPet.getName());
                    pet.setPrice(newPet.getPrice());
                    pet.setDescription(newPet.getDescription());
                    pet.setAdoption(adoption);
                    return petRepository.save(pet);
                })
                .orElseGet(() -> {
                    Pet pet=petRepository.findById(id).get();
                    return petRepository.save(pet);
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

    public List<Pet> getPetIdAutocomplete( String query)
    {

        List<Pet> pets=petRepository.findAll();

        return pets.stream()
                .filter(pet -> pet.getId().toString().startsWith(query))
                .collect(Collectors.toList());
    }
}
