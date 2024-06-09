package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.model.User;
import com.skypro.animalShelterInfoBot.repositories.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private Collection<Animal> animals;


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

    public Animal updateAnimalForUser(long id, Animal animal, User user) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        Animal updatedAnimalForUser = animalRepository.findAnimalById(id);
        if (updatedAnimalForUser == null) {
            throw new IllegalArgumentException("Animal with id " + id + " not found");
        }
        updatedAnimalForUser.setUser(user);
        return animalRepository.save(updatedAnimalForUser);
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

    public String findDogByColor(String color) {
        List<Animal> dogList = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.DOG);
        return dogList.stream()
                .filter(animal -> animal.getColor()
                        .equalsIgnoreCase(color))
                .toList().toString().replace("[", "").replace("]", "").replace(",", "");
    }

    public String findCatByColor(String color) {
        List<Animal> catList = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.CAT);
        return catList.stream()
                .filter(animal -> animal.getColor()
                        .equalsIgnoreCase(color))
                .toList().toString().replace("[", "").replace("]", "").replace(",", "");
    }

    public String findDogByAgeBetween(float age, float age2) {
        List<Animal> dogList = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.DOG);
        return dogList.stream()
                .filter(animal -> animal.getAge() >= age && animal.getAge() <= age2)
                .toList().toString().replace("[", "").replace("]", "").replace(",", "");
    }

    public String findCatByAgeBetween(float age, float age2) {
        List<Animal> catList = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.CAT);
        return catList.stream()
                .filter(animal -> animal.getAge() >= age && animal.getAge() <= age2)
                .toList().toString().replace("[", "").replace("]", "").replace(",", "");
    }
    public String findCatByBreed(String breed) {
        List<Animal> catList = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.CAT);
        return catList.stream()
                .filter(animal -> animal.getBreed()
                        .equalsIgnoreCase(breed))
                .toList().toString().replace("[", "").replace("]", "").replace(",", "");
    }
    public String findDogByBreed(String breed) {
        List<Animal> dogList = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.DOG);
        return dogList.stream()
                .filter(animal -> animal.getBreed()
                        .equalsIgnoreCase(breed))
                .toList().toString().replace("[", "").replace("]", "").replace(",", "");
    }

}
