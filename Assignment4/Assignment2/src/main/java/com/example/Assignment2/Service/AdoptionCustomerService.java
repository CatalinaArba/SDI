package com.example.Assignment2.Service;

import com.example.Assignment2.Exception.PetNotFoundException;
import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Long countAll(){
        return adoptionCustomerRepository.count();
    }

    public List<AdoptionCustomerDTOWithId> all(PageRequest pr) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AdoptionCustomer.class, AdoptionCustomerDTOWithId.class);
        //.addMapping(src -> src.getAdoptionAdoptionCustomer().getId(),
        //       AdoptionCustomerDTOWithId::setIdAdoptionAdoptionCustomer)
        //.addMapping(src -> src.getCustomerAdoptionCustomer().getId(),
        //      AdoptionCustomerDTOWithId::setIdCustomerAdoptionCustomer);

        Page<AdoptionCustomer> allAdoptionCustomers = adoptionCustomerRepository.findAll(pr);
        return allAdoptionCustomers.stream()
                .map(adoptionCustomer -> modelMapper.map(adoptionCustomer, AdoptionCustomerDTOWithId.class))
                .collect(Collectors.toList());
    }



    public AdoptionCustomer newAdoptonCustomer( AdoptionCustomerDTOWithId newAdoptionCustomer) {
        Adoption adoption=adoptionRepository.findById(newAdoptionCustomer.getIdAdoptionAdoptionCustomer()).get();
        Customer customer=customerRepository.findById(newAdoptionCustomer.getIdCustomerAdoptionCustomer()).get();

        ModelMapper modelMapper = new ModelMapper();
        TypeMap<AdoptionCustomerDTOWithId, AdoptionCustomer> typeMap = modelMapper.createTypeMap(AdoptionCustomerDTOWithId.class, AdoptionCustomer.class);
//        typeMap.addMappings(mapping -> {
//            mapping.map(AdoptionCustomerDTOWithId::getAdoptionContract, AdoptionCustomer::setAdoptionContract);
//            mapping.map(AdoptionCustomerDTOWithId::getCustomerFeedback, AdoptionCustomer::setCustomerFeedback);
//        });
        AdoptionCustomer adoptionCustomer = modelMapper.map(newAdoptionCustomer, AdoptionCustomer.class);
        adoptionCustomer.setAdoptionAdoptionCustomer(adoption);
        adoptionCustomer.setCustomerAdoptionCustomer(customer);

        return adoptionCustomerRepository.save(adoptionCustomer);

        //this was before i addded the frontend part where i add using a dto, not the whole object
//        newAdoptionCustomer.setCustomerAdoptionCustomer(customer);
//        newAdoptionCustomer.setAdoptionAdoptionCustomer(adoption);
//        newAdoptionCustomer=adoptionCustomerRepository.save(newAdoptionCustomer);
//
//        adoption.getAdoptionCustomers().add(newAdoptionCustomer);
//        customer.getAdoptionCustomers().add(newAdoptionCustomer);
//        return newAdoptionCustomer;
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
