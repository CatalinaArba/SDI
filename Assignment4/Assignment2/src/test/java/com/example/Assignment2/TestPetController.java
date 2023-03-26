package com.example.Assignment2;

import com.example.Assignment2.Controller.PetController;
import com.example.Assignment2.Model.Pet;
import com.example.Assignment2.Repository.IAdoptionRepository;
import com.example.Assignment2.Repository.IPetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TestPetController {
    @Mock
    private IPetRepository petRepository;

    private PetController petController;

    @Before
    public void setUp() {
        petController = new PetController(petRepository);
    }
    @Test
    public void testPetControllerFilterByPrice()
    {
        // Create a list of pets with prices greater than or equal to the minimum price
        Pet pet1 = new Pet("Fluffy", "cat",1,"Male", 100);
        Pet pet2 = new Pet("Rex", "dog", 2,"Male",200);
        List<Pet> pets = Arrays.asList(pet1, pet2);
        List<Pet> expensivePets=Arrays.asList(pet2);
        // Mock the behavior of the petRepository to return the list of pets when findByPriceGreaterThanEqual is called
        when(petRepository.findByPriceGreaterThanEqual(100)).thenReturn(pets);
        when(petRepository.findByPriceGreaterThanEqual(150)).thenReturn(expensivePets);

        // Call the byPrice method with a minimum price of 100 and verify that it returns the expected list of pets
        List<Pet> result = petController.byPrice(100);
        assertEquals(pets, result);

        List<Pet> expensiveResult = petController.byPrice(150);
        assertEquals(expensivePets, expensiveResult);

        // Verify that findByPriceGreaterThanEqual was called on the petRepository with the correct argument
        verify(petRepository, times(1)).findByPriceGreaterThanEqual(100);
    }
}





