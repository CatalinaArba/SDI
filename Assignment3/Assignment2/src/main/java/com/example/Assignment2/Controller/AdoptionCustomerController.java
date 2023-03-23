package com.example.Assignment2.Controller;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdoptionCustomerController {
    @Autowired
    private final IAdoptionCustomerRepository adoptionCustomerRepository;
    private final IAdoptionRepository adoptionRepository;
    private final ICustomerRepository customerRepository;

    public AdoptionCustomerController(IAdoptionCustomerRepository adoptionCustomerRepository, IAdoptionRepository adoptionRepository, ICustomerRepository customerRepository) {
        this.adoptionCustomerRepository = adoptionCustomerRepository;
        this.adoptionRepository = adoptionRepository;
        this.customerRepository = customerRepository;
    }


    @GetMapping("/adoptionCustomer")
    public List<AdoptionCustomerDTOWithId> all() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AdoptionCustomer.class, AdoptionCustomerDTOWithId.class);
                //.addMapping(src -> src.getAdoptionAdoptionCustomer().getId(),
                 //       AdoptionCustomerDTOWithId::setIdAdoptionAdoptionCustomer)
                //.addMapping(src -> src.getCustomerAdoptionCustomer().getId(),
                  //      AdoptionCustomerDTOWithId::setIdCustomerAdoptionCustomer);

        List<AdoptionCustomer> allAdoptionCustomers = adoptionCustomerRepository.findAll();
        return allAdoptionCustomers.stream()
                .map(adoptionCustomer -> modelMapper.map(adoptionCustomer, AdoptionCustomerDTOWithId.class))
                .collect(Collectors.toList());
    }


    @PostMapping("/adoptionCustomer/{adoptionId}/{customerId}")
    AdoptionCustomer newAdoptonCustomer(@RequestBody AdoptionCustomer newAdoptionCustomer,@PathVariable Integer adoptionId,@PathVariable Integer customerId) {
        Adoption adoption=adoptionRepository.findById(adoptionId).get();
        Customer customer=customerRepository.findById(customerId).get();
        newAdoptionCustomer.setCustomerAdoptionCustomer(customer);
        newAdoptionCustomer.setAdoptionAdoptionCustomer(adoption);
        newAdoptionCustomer=adoptionCustomerRepository.save(newAdoptionCustomer);

        adoption.getAdoptionCustomers().add(newAdoptionCustomer);
        customer.getAdoptionCustomers().add(newAdoptionCustomer);
        return newAdoptionCustomer;
    }


    @GetMapping("/adoptionCustomer/{id}")
    AdoptionCustomerDTO one(@PathVariable Integer id) {
        if (adoptionCustomerRepository.findById(id).isEmpty())
            throw new PetNotFoundException(id);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AdoptionCustomer.class, AdoptionCustomerDTO.class);
                //.addMapping(AdoptionCustomer::getAdoptionAdoptionCustomer,
                 //      AdoptionCustomerDTO::setAdoptionAdoptionCustomer)
                //.addMapping(AdoptionCustomer::getCustomerAdoptionCustomer,
                 //       AdoptionCustomerDTO::setCustomerAdoptionCustomer);

        AdoptionCustomer adoptionCustomer=adoptionCustomerRepository.findById(id).get();
        return  modelMapper.map(adoptionCustomer, AdoptionCustomerDTO.class);
    }

    @PutMapping("/adoptionCustomer/{id}")
    AdoptionCustomer updateAdoptionCustomer(@RequestBody AdoptionCustomer newAdoptionCustomer, @PathVariable Integer id) {

        AdoptionCustomer oldAdoptionCustomer=adoptionCustomerRepository.findById(id).get();
        return adoptionCustomerRepository.findById(id)
                .map(adoptionCustomer -> {
                    adoptionCustomer.setCustomerFeedback(newAdoptionCustomer.getCustomerFeedback());
                    adoptionCustomer.setAdoptionContract(newAdoptionCustomer.getAdoptionContract());
                    adoptionCustomer.setCustomerAdoptionCustomer(oldAdoptionCustomer.getCustomerAdoptionCustomer());
                    adoptionCustomer.setAdoptionAdoptionCustomer(oldAdoptionCustomer.getAdoptionAdoptionCustomer());
                    return adoptionCustomerRepository.save(adoptionCustomer);
                })
                .orElseGet(() -> {
                    newAdoptionCustomer.setId(id);
                    return adoptionCustomerRepository.save(newAdoptionCustomer);
                });
    }

    @DeleteMapping("/adoptionCustomer/{id}")
    void deletePet(@PathVariable Integer id) {
        adoptionCustomerRepository.deleteById(id);
    }
}
