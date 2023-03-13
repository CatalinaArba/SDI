package com.example.Assignment2.Controller;

import com.example.Assignment2.Exception.CustomerNotFoundException;
import com.example.Assignment2.Model.Customer;
import com.example.Assignment2.Repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private final ICustomerRepository repository;

    CustomerController(ICustomerRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/customers")
    List<Customer> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }

    // Single item

    @GetMapping("/customers/{id}")
    Customer one(@PathVariable Integer id) {

        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Integer id) {

        return repository.findById(id)
                .map(customer -> {
                    customer.setAdress(newCustomer.getAdress());
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setMail(newCustomer.getMail());
                    customer.setPhone(newCustomer.getPhone());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
    }

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
