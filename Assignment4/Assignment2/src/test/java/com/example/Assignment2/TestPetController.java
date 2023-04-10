package com.example.Assignment2;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import com.example.Assignment2.Controller.PetController;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Repository.IPetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class TestPetController {

    @Mock
    private IPetRepository petRepository;

    @InjectMocks
    private PetController petController;

    @Test
    public void testByPriceEndpoint() throws Exception {
        Pet pet1 = new Pet("Fluffy", "cat",1,"Male", 100,"wow");
        Pet pet2 = new Pet("Rex", "dog", 2,"Male",200,"Wonderful");
        List<Pet> pets = Arrays.asList(pet1, pet2);
        List<Pet> expensivePets2 = Arrays.asList(pet2);

        when(petRepository.findByPriceGreaterThanEqual(50)).thenReturn(pets);

        MockMvc mockMvc = standaloneSetup(petController).build();
        mockMvc.perform(get("/pets/price/50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fluffy"))
                .andExpect(jsonPath("$[0].price").value(100))
                .andExpect(jsonPath("$[1].name").value("Rex"))
                .andExpect(jsonPath("$[1].price").value(200));


        when(petRepository.findByPriceGreaterThanEqual(150)).thenReturn(expensivePets2);

        MockMvc mockMvc2 = standaloneSetup(petController).build();
        mockMvc2.perform(get("/pets/price/150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rex"))
                .andExpect(jsonPath("$[0].price").value(200));
    }
}


