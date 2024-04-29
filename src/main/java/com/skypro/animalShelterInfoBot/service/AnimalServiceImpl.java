package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private Collection<Animal> animals;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(long id, Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        Animal updatedAnimal = animalRepository.findAnimalById(id);
        if (updatedAnimal == null) {
            throw new IllegalArgumentException("Animal with id " + id + " not found");
        }
        updatedAnimal.setCatOrDog(animal.getCatOrDog());
        updatedAnimal.setNickName(animal.getNickName());
        updatedAnimal.setBreed(animal.getBreed());
        updatedAnimal.setAge(animal.getAge());
        updatedAnimal.setColor(animal.getColor());
        return animalRepository.save(updatedAnimal);
    }

    public Object getAnimalsPagination(Integer pageNumber, Integer sizeNumber) {
        if (pageNumber != null && sizeNumber != null) {
            int startIndex = (pageNumber - 1) * sizeNumber;
            int endIndex = Math.min(startIndex + sizeNumber, animals.size());

            return animals.size();
        } else {
            throw new IllegalArgumentException("Page number and size number must not be null");
        }
    }


    public List<Animal> getAllAnimals() {

        return animalRepository.findAll();
    }

    public void deleteAnimalById(Long Id) {
        if (Id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        animalRepository.deleteById(Id);
    }

    public Animal findAnimal(Long animalId) {
        return animalRepository.findAnimalById(animalId);
    }
}
