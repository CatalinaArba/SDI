

package com.example.Assignment2.Controller;
import com.example.Assignment2.Exception.AdoptionNotFoundException;
import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import com.example.Assignment2.Service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PetController {

    @Autowired
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;

    }
    @GetMapping("/pets/count")
    Long countAll() {
        return petService.countAll();
    }

    @GetMapping("/pets/page/{page}/size/{size}")
    List<PetDTOWithId> all(@PathVariable int page, @PathVariable int size) {
        PageRequest pr=PageRequest.of(page,size);
        return petService.all(pr);
    }

    @PostMapping("/pets/add")
    Pet newPet(@Valid @RequestBody PetDTOWithId newPet) {
        return petService.newPet(newPet);
    }


    @GetMapping("/pets/{id}/details")
    PetDTO one(@PathVariable String id) {
        return petService.one(id);
    }

    @PutMapping("/pets/{id}")
    Pet replacePet(@RequestBody PetDTOWithId newPet, @PathVariable Integer id) {
        return petService.replacePet(newPet,id);
    }

    @DeleteMapping("/pets/{id}/delete")
    void deletePet(@PathVariable Integer id) {
        petService.deletePet(id);
    }

    @GetMapping("/pets/price/{minPrice}/page/{page}/size/{size}")
    public List<Pet> byPrice(@PathVariable Integer minPrice,@PathVariable int page, @PathVariable int size) {
        PageRequest pr=PageRequest.of(page,size);
        return petService.byPrice(minPrice,pr);
    }

    @GetMapping("/pets/price")
    public List<Pet> sortByPrice() {
        return petService.sortByPrice();
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

    @GetMapping("/pets/autocomplete")
    public List<Pet> get(@RequestParam String query)
    {
        return this.petService.getPetIdAutocomplete(query);
    }

}