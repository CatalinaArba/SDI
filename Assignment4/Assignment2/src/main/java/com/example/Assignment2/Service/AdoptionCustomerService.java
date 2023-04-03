package com.example.Assignment2.Service;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionCustomerService {
    @Autowired
    private final IAdoptionCustomerRepository adoptionCustomerRepository;
    private final IAdoptionRepository adoptionRepository;
    private final ICustomerRepository customerRepository;

    public AdoptionCustomerService(IAdoptionCustomerRepository adoptionCustomerRepository, IAdoptionRepository adoptionRepository, ICustomerRepository customerRepository) {
        this.adoptionCustomerRepository = adoptionCustomerRepository;
        this.adoptionRepository = adoptionRepository;
        this.customerRepository = customerRepository;
    }


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



    public AdoptionCustomer newAdoptonCustomer( AdoptionCustomer newAdoptionCustomer,  Integer adoptionId, @PathVariable Integer customerId) {
        Adoption adoption=adoptionRepository.findById(adoptionId).get();
        Customer customer=customerRepository.findById(customerId).get();
        newAdoptionCustomer.setCustomerAdoptionCustomer(customer);
        newAdoptionCustomer.setAdoptionAdoptionCustomer(adoption);
        newAdoptionCustomer=adoptionCustomerRepository.save(newAdoptionCustomer);

        adoption.getAdoptionCustomers().add(newAdoptionCustomer);
        customer.getAdoptionCustomers().add(newAdoptionCustomer);
        return newAdoptionCustomer;
    }


    public List<AdoptionCustomer> newAdoptonCustomerList( List<AdoptionCustomerDTOWithId> AdoptionCustomerList, Integer customerId) {
        Customer selectedCustomer=customerRepository.findById(customerId).get();
        List<AdoptionCustomer> finalLits= new ArrayList<>();
        for(AdoptionCustomerDTOWithId acdto:AdoptionCustomerList){
            AdoptionCustomer ac=new AdoptionCustomer();
            ac.setAdoptionContract(acdto.getAdoptionContract());
            ac.setCustomerFeedback(acdto.getCustomerFeedback());
            ac.setCustomerAdoptionCustomer(selectedCustomer);
            Adoption selectedAdoption=adoptionRepository.findById(acdto.getIdAdoptionAdoptionCustomer()).get();
            ac.setAdoptionAdoptionCustomer(selectedAdoption);
            ac=adoptionCustomerRepository.save(ac);
            finalLits.add(ac);
        }
        return finalLits;
    }


    public AdoptionCustomerDTO one( Integer id) {
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

    public AdoptionCustomer updateAdoptionCustomer( AdoptionCustomer newAdoptionCustomer,  Integer id) {

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


    public void deletePet( Integer id) {
        adoptionCustomerRepository.deleteById(id);
}}
