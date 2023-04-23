package com.example.Assignment2.Service;

import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomerService {
    @Autowired
    private final ICustomerRepository customerRepository;

    private IAdoptionCustomerRepository adoptionCustomerRepository;

    CustomerService(ICustomerRepository customerRepository,IAdoptionCustomerRepository adoptionCustomerRepository) {
        this.customerRepository = customerRepository;
        this.adoptionCustomerRepository=adoptionCustomerRepository;
    }

    public List<CustomerDTO> all() {
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.typeMap(Customer.class, CustomerDTO.class).addMapping(Customer::getAddress,CustomerDTO::setAddress);
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO> customerDTOS=customers.stream()
                .map(customer->modelMapper.map(customer,CustomerDTO.class)).collect(Collectors.toList());
        return customerDTOS;
    }



    public Customer newCustomer( Customer newCustomer) {

        return customerRepository.save(newCustomer);
    }


    public CustomerDTOWithAdoptionId one( Integer id) {

        Customer customer=customerRepository.findById(id).get();
        CustomerDTOWithAdoptionId customerDTOWithAdoptionId=new CustomerDTOWithAdoptionId();
        List<Integer> adoptionIds=new ArrayList<>();
        List<AdoptionCustomer> adoptionCustomers=adoptionCustomerRepository.findAll();
        for(AdoptionCustomer ac:adoptionCustomers)
            if(ac.getCustomerAdoptionCustomer().getId()== customer.getId())
                adoptionIds.add(ac.getAdoptionAdoptionCustomer().getId());
        customerDTOWithAdoptionId.setAdoptionIds(adoptionIds);
        customerDTOWithAdoptionId.setCustomer(customer);
        return  customerDTOWithAdoptionId;
    }


    public Customer replaceCustomer( Customer newCustomer,  Integer id) {

        Customer oldCustomer=customerRepository.findById(id).get();
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setAddress(newCustomer.getAddress());
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setMail(newCustomer.getMail());
                    customer.setPhone(newCustomer.getPhone());
                    customer.setAdoptionCustomers(oldCustomer.getAdoptionCustomers());
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return customerRepository.save(newCustomer);
                });
    }


    public void deleteCustomer( Integer id) {
        customerRepository.deleteById(id);
    }



    public List<CustomerDTOSatisticsNoCoustomer> statisticsNoCustomers()
    {
        List<AdoptionCustomer> adoptionCustomerList=adoptionCustomerRepository.findAll();

        List<Customer> customerList=customerRepository.findAll();

        List<CustomerDTOSatisticsNoCoustomer> finalList=new ArrayList<>();

        for(Customer c:customerList){
            Integer no=0;
            for(AdoptionCustomer ac:adoptionCustomerList){
                if (ac.getCustomerAdoptionCustomer().getId()==c.getId())
                {
                    Integer adoptionID=ac.getAdoptionAdoptionCustomer().getId();
                    for(AdoptionCustomer ac2:adoptionCustomerList)
                    {
                        if (ac2.getAdoptionAdoptionCustomer().getId()==adoptionID &&ac2.getCustomerAdoptionCustomer().getId()!=c.getId())
                            no++;
                    }
                }
            }
            CustomerDTOSatisticsNoCoustomer customerDTOSatisticsNoCoustomer=new CustomerDTOSatisticsNoCoustomer();
            customerDTOSatisticsNoCoustomer.setId(c.getId());
            customerDTOSatisticsNoCoustomer.setAddress(c.getAddress());
            customerDTOSatisticsNoCoustomer.setFirstName(c.getFirstName());
            customerDTOSatisticsNoCoustomer.setLastName(c.getLastName());
            customerDTOSatisticsNoCoustomer.setPhone(c.getPhone());
            customerDTOSatisticsNoCoustomer.setMail(c.getMail());
            customerDTOSatisticsNoCoustomer.setNoCustomers(no);
            finalList.add(customerDTOSatisticsNoCoustomer);
        }
        finalList.sort(Comparator.comparingInt(CustomerDTOSatisticsNoCoustomer::getNoCustomers).reversed());
        return finalList;
    }

    public List<Customer> getCustomersNameAutocomplete( String query)
    {

        List<Customer> customers=customerRepository.findAll();

        return customers.stream()
                .filter(adoption -> adoption.getFirstName().toLowerCase().contains(query.toLowerCase())).limit(20)
                .collect(Collectors.toList());
    }


}
