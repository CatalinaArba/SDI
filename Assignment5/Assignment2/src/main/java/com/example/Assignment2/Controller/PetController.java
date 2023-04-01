

package com.example.Assignment2.Controller;
import com.example.Assignment2.Exception.AdoptionNotFoundException;
import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PetController {
    @Autowired
    private final IPetRepository petRepository;


    public PetController(IPetRepository petRepository) {
        this.petRepository = petRepository;

    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/pets")
    List<PetDTOWithId> all() {
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.typeMap(Pet.class, PetDTOWithId.class);
        List<PetDTOWithId> petDTOsWithIds = petRepository.findAll().stream()
                .map(pet -> modelMapper.map(pet, PetDTOWithId.class))
                .collect(Collectors.toList());
        return petDTOsWithIds;

        //Same thing using map function: it works
        //return petRepository.findAll().stream().map(m->m.toPetDTOWithId()).collect(Collectors.toList());
    }

    @PostMapping("/pets")
    Pet newPet(@Valid @RequestBody Pet newPet) {
        return petRepository.save(newPet);
    }

    // Single item
    @GetMapping("/pets/{id}")
    PetDTO one(@PathVariable Integer id) {
        if (petRepository.findById(id).isEmpty())
            throw new PetNotFoundException(id);

        ModelMapper modelMapper = new ModelMapper();
        PetDTO petDTO = modelMapper.map(petRepository.findById(id).get(), PetDTO.class);
        return petDTO;

    }

    @PutMapping("/pets/{id}")
    Pet replacePet(@RequestBody Pet newPet, @PathVariable Integer id) {

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

    @DeleteMapping("/pets/{id}")
    void deletePet(@PathVariable Integer id) {
        petRepository.deleteById(id);
    }

    @GetMapping("/pets/price/{minPrice}")
    public List<Pet> byPrice(@PathVariable Integer minPrice) {
        return petRepository.findByPriceGreaterThanEqual(minPrice);
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
}