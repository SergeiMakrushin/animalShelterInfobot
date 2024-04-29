package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.Animal;

import java.util.List;

public interface AnimalService {
    Animal createAnimal(Animal animal);

    Animal updateAnimal(long id, Animal animal);

    Object getAnimalsPagination(Integer pageNumber, Integer sizeNumber);

    List<Animal> getAllAnimals();

    void deleteAnimalById(Long Id);

    Animal findAnimal(Long animalId);


}
