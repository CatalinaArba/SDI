package com.example.Assignment2.Repository;

import com.example.Assignment2.Model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface IAdoptionRepository extends JpaRepository<Adoption, Integer> {
}