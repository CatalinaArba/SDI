package com.example.Assignment2.Repository;

import com.example.Assignment2.Model.AdoptionCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface IAdoptionCustomerRepository extends JpaRepository<AdoptionCustomer, Integer> {

}
