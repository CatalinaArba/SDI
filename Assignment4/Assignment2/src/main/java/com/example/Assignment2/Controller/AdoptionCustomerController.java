package com.example.Assignment2.Controller;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import com.example.Assignment2.Service.AdoptionCustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdoptionCustomerController {
    @Autowired
    private final AdoptionCustomerService adoptionCustomerService;


    public AdoptionCustomerController(AdoptionCustomerService adoptionCustomerService) {
        this.adoptionCustomerService = adoptionCustomerService;
    }

    @GetMapping("/adoptionCustomer/page/{page}/size/{size}")
    public List<AdoptionCustomerDTOWithId> all(@PathVariable int page, @PathVariable int size) {
        PageRequest pr=PageRequest.of(page,size);
        return adoptionCustomerService.all(pr);
    }

    @GetMapping("/adoptionCustomer/count")
    Long countAll() {
        return adoptionCustomerService.countAll();
    }

    @PostMapping("/adoptionCustomer")
    AdoptionCustomer newAdoptonCustomer(@RequestBody AdoptionCustomerDTOWithId newAdoptionCustomer) {
        return adoptionCustomerService.newAdoptonCustomer(newAdoptionCustomer);
    }

    @PostMapping("/adoptionCustomer/{customerId}")
    List<AdoptionCustomer> newAdoptonCustomerList(@RequestBody List<AdoptionCustomerDTOWithId> AdoptionCustomerList,@PathVariable Integer customerId) {
        return adoptionCustomerService.newAdoptonCustomerList(AdoptionCustomerList,customerId);
    }


    @GetMapping("/adoptionCustomer/{id}")
    AdoptionCustomerDTO one(@PathVariable Integer id) {
        return adoptionCustomerService.one(id);
    }

    @PutMapping("/adoptionCustomer/{id}")
    AdoptionCustomer updateAdoptionCustomer(@RequestBody AdoptionCustomer newAdoptionCustomer, @PathVariable Integer id) {

        return adoptionCustomerService.updateAdoptionCustomer(newAdoptionCustomer,id);
    }

    @DeleteMapping("/adoptionCustomer/{id}")
    void deletePet(@PathVariable Integer id) {
        adoptionCustomerService.deletePet(id);
    }
}
