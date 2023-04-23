package com.example.Assignment2.Controller;

import com.example.Assignment2.Model.*;
import com.example.Assignment2.Repository.IAdoptionCustomerRepository;
import com.example.Assignment2.Repository.ICustomerRepository;
import com.example.Assignment2.Service.AdoptionService;
import com.example.Assignment2.Service.CustomerService;
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
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    List<CustomerDTO> all() {
        return customerService.all();
    }


    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newCustomer) {

        return customerService.newCustomer(newCustomer);
    }

    // Single item
    @GetMapping("/customers/{id}")
    CustomerDTOWithAdoptionId one(@PathVariable Integer id) {

        return customerService.one(id);
    }

    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Integer id) {

        return replaceCustomer(newCustomer,id);
    }

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Integer id) {

        customerService.deleteCustomer(id);
    }


    @GetMapping("/customers/noOtherCustomerForAdoptions")
    List<CustomerDTOSatisticsNoCoustomer> statisticsNoCustomers()
    {
       return customerService.statisticsNoCustomers();
    }

    @GetMapping("/customers/autocomplete")
    public List<Customer> getCustomersSuggestions(@RequestParam String query)
    {
        return this.customerService.getCustomersNameAutocomplete(query);
    }

}
