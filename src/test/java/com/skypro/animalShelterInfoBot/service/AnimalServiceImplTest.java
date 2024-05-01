package com.skypro.animalShelterInfoBot.service;
import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.repositories.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalServiceImpl animalService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAnimal() {
        Animal animal = new Animal("Dog", "Buddy", "Golden Retriever", 5, "Golden");
        when(animalRepository.save(animal)).thenReturn(animal);

        Animal createdAnimal = animalService.createAnimal(animal);

        assertEquals(animal, createdAnimal);
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    public void testUpdateAnimal() {
        long id = 1L;
        Animal animal = new Animal("Cat", "Whiskers", "Siamese", 3, "White");
        Animal updatedAnimal = new Animal("Cat", "Whiskers", "Siamese", 3, "Grey");

        when(animalRepository.findAnimalById(id)).thenReturn(animal);
        when(animalRepository.save(updatedAnimal)).thenReturn(updatedAnimal);

        Animal result = animalService.updateAnimal(id, updatedAnimal);

        assertEquals(updatedAnimal, result);
        verify(animalRepository, times(1)).findAnimalById(id);
        verify(animalRepository, times(1)).save(updatedAnimal);
    }

    @Test
    public void testGetAllAnimals() {
        List<Animal> animalList = new ArrayList<>();
        animalList.add(new Animal("Dog", "Max", "Labrador", 4, "Black"));
        animalList.add(new Animal("Cat", "Fluffy", "Persian", 2, "Grey"));

        when(animalRepository.findAll()).thenReturn(animalList);

        List<Animal> result = animalService.getAllAnimals();

        assertEquals(animalList, result);
        verify(animalRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteAnimalById() {
        Long id = 1L;

        animalService.deleteAnimalById(id);

        verify(animalRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindAnimal() {
        long animalId = 1L;
        Animal animal = new Animal("Dog", "Rocky", "Bulldog", 6, "Brown");

        when(animalRepository.findAnimalById(animalId)).thenReturn(animal);

        Animal result = animalService.findAnimal(animalId);

        assertEquals(animal, result);
        verify(animalRepository, times(1)).findAnimalById(animalId);
    }

}