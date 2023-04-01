package com.example.Assignment2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Assignment2.Controller.AdoptionController;
import com.example.Assignment2.Model.Adoption;
import com.example.Assignment2.Model.AdoptionDTOStatisticsPetsPrice;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAdoptionController {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private IAdoptionRepository adoptionRepository;

    @MockBean
    private IPetRepository petRepository;

    @Test
    public void testGetAllAdoptionsOrderByAvgPetPrice() {
        // Create test data
        Pet pet1 = new Pet("Fido", "dob", 10, "Male", 100);
        Pet pet2 = new Pet("Whiskers", "cat", 4, "Male", 75);
        Adoption adoption1 = new Adoption(LocalDate.of(2022, 4, 1), 160, "in process", "Campia turzii, Cluj", "All good", Arrays.asList(pet1, pet2));
        Pet pet3 = new Pet("Tweety", "bird", 1, "Male", 50);
        Adoption adoption2 = new Adoption(LocalDate.of(2023, 3, 11), 100, "Done", "Cluj-Napoca, Cluj", "All fine!", Arrays.asList(pet3));
        List<Adoption> adoptions = Arrays.asList(adoption1, adoption2);

        // Configure the mock behavior
        when(adoptionRepository.findAll()).thenReturn(adoptions);

        // Send GET request to the endpoint
        ResponseEntity<List<AdoptionDTOStatisticsPetsPrice>> response = restTemplate.exchange(
                "/adoptions/statistics",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdoptionDTOStatisticsPetsPrice>>() {
                });

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AdoptionDTOStatisticsPetsPrice> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(adoption1.getAdoptionDate(), result.get(0).getAdoptionDate());
        assertEquals(adoption1.getAdoptionFee(), result.get(0).getAdoptionFee());
        assertEquals(adoption1.getAdoptionStatus(), result.get(0).getAdoptionStatus());
        assertEquals(adoption1.getAdoptionLocation(), result.get(0).getAdoptionLocation());
        assertEquals(adoption1.getAdoptionNotes(), result.get(0).getAdoptionNotes());
        assertEquals(87.5, result.get(0).getAvgPetPrice());
        assertEquals(adoption2.getAdoptionDate(), result.get(1).getAdoptionDate());
        assertEquals(adoption2.getAdoptionFee(), result.get(1).getAdoptionFee());
        assertEquals(adoption2.getAdoptionStatus(), result.get(1).getAdoptionStatus());
        assertEquals(adoption2.getAdoptionLocation(), result.get(1).getAdoptionLocation());
        assertEquals(adoption2.getAdoptionNotes(), result.get(1).getAdoptionNotes());
        assertEquals(50, result.get(1).getAvgPetPrice());
    }
}

