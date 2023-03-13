package com.example.Assignment2.Repository;
import com.example.Assignment2.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
}
