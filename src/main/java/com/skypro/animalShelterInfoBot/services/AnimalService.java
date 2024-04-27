package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.animals.Animal;

import java.util.List;

public interface AnimalService {
    Animal createAnimal(Animal animal);

    Animal updateAnimal(long id, Animal animal);

    Object getAnimalsPagination(Integer pageNumber, Integer sizeNumber);

    List<Animal> getAllAnimals();

    void deleteAnimalById(Long Id);

    Animal findAnimal(Long animalId);


}
