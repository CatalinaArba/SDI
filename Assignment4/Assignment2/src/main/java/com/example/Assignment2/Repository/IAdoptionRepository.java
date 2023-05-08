package com.example.Assignment2.Repository;

import com.example.Assignment2.Model.Adoption;
import com.example.Assignment2.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Component
public interface IAdoptionRepository extends JpaRepository<Adoption, Integer> {

    default List<Pet> findPetsByAdoptionId(int id) {
        Optional<Adoption> optionalAdoption = findById(id);
        return optionalAdoption
                .map(adoption -> adoption.getPet().stream().collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}