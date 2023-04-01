package com.example.Assignment2.Controller;

import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {
    @Autowired
    private final ICustomerRepository customerRepository;

    private IAdoptionCustomerRepository adoptionCustomerRepository;

    CustomerController(ICustomerRepository customerRepository,IAdoptionCustomerRepository adoptionCustomerRepository) {
        this.customerRepository = customerRepository;
        this.adoptionCustomerRepository=adoptionCustomerRepository;
    }


    @GetMapping("/customers")
    List<CustomerDTO> all() {
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.typeMap(Customer.class, CustomerDTO.class).addMapping(Customer::getAddress,CustomerDTO::setAddress);
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO> customerDTOS=customers.stream()
                .map(customer->modelMapper.map(customer,CustomerDTO.class)).collect(Collectors.toList());
        return customerDTOS;
    }


    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newCustomer) {

        return customerRepository.save(newCustomer);
    }

    // Single item
    @GetMapping("/customers/{id}")
    CustomerDTOWithAdoptionId one(@PathVariable Integer id) {

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

    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Integer id) {

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

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }


    @GetMapping("/customers/noOtherCustomerForAdoptions")
    List<CustomerDTOSatisticsNoCoustomer> statisticsNoCustomers()
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
}
