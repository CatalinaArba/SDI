package com.example.Assignment2.Repository;

import com.example.Assignment2.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Component
public interface IPetRepository extends JpaRepository<Pet, Integer> {
    @Query("SELECT e FROM Pet e WHERE e.price >= :minPrice")
    List<Pet> findByPriceGreaterThanEqual(@Param("minPrice") Integer minPrice);

    List<Pet> findByAdoptionId(int adoptionId);



}

