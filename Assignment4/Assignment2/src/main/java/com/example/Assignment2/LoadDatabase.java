package com.example.Assignment2;

import com.example.Assignment2.Model.Customer;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Repository.ICustomerRepository;
import com.example.Assignment2.Repository.IPetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ICustomerRepository customerRepository, IPetRepository petRepository) {

        return args -> {
            /*log.info("Preloading " + customerRepository.save(new Customer("John", "Doe", "123 Main St, Anytown USA", "johndoe@email.com", "555-1234")));
            log.info("Preloading " + customerRepository.save(new   Customer("John", "Doe", "123 Main St, Anytown USA", "johndoe@email.com", "555-1234")));
            log.info("Preloading " + customerRepository.save(new  Customer("Jane", "Smith", "456 Elm Ave, Another City USA", "janesmith@email.com", "555-5678")));
            log.info("Preloading " + customerRepository.save(new  Customer("Bob", "Johnson", "789 Oak Blvd, Somewhere USA", "bobjohnson@email.com", "555-9012")));
            log.info("Preloading " + customerRepository.save(new Customer("Sara", "Lee", "101 Cherry Ln, Anywhere USA", "saralee@email.com", "555-3456")));
            log.info("Preloading " + customerRepository.save(new Customer("Mike", "Jones", "246 Pine St, Nowhere USA", "mikejones@email.com", "555-7890")));
*/
            //log.info("Preloading " + petRepository.save(new
            /*log.info("Preloading " + petRepository.save(new Pet("Buddy", "Dog", 3, "Male", 500)));
            log.info("Preloading " + petRepository.save(new Pet("Fluffy", "Cat", 2, "Female", 250)));
            log.info("Preloading " + petRepository.save(new Pet("Max", "Dog", 5, "Male", 700)));
            log.info("Preloading " + petRepository.save(new Pet("Mittens", "Cat", 1, "Female", 150)));
            log.info("Preloading " + petRepository.save(new Pet("Rocky", "Dog", 2, "Male", 450)));
            log.info("Preloading " + petRepository.save(new Pet("Whiskers", "Cat", 3, "Male", 200)));
            log.info("Preloading " + petRepository.save(new Pet("Lola", "Dog", 4, "Female", 600)));
            log.info("Preloading " + petRepository.save(new Pet("Boots", "Cat", 4, "Male", 175)));
            log.info("Preloading " + petRepository.save(new Pet("Daisy", "Dog", 1, "Female", 350)));
            log.info("Preloading " + petRepository.save(new Pet("Socks", "Cat", 2, "Female", 125)));
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : customerRepository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");*/


        };






    }
}

/*      Customer Postman
        "firstName":"Georgjiernvuicdkcmeeta",
        "lastName":"Andreica",
        "adress":"Str Plopilor",
        "phone":"079999999",
        "mail":"getaandrica@email.com"
*/

/*      Adoption Postman
        "adoptionDate":"2022-11-10",
        "adoptionFee":200,
        "adoptionStatus":"in process",
        "adoptionLocation":"Turda,Cluj",
        "adoptionNotes":"adopted by a family"
*/
/*      Pet Postman
        "name":"Bobitaaaaa",
        "petType":"Doooog",
        "age":2,
        "gender":"Male",
        "price":200
*/