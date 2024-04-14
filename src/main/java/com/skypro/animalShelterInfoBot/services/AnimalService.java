package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.animals.Animal;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.repositories.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
    public class AnimalService {
        private final AnimalRepository animalRepository;
        private Collection<Animal> animals;

        public AnimalService(AnimalRepository animalRepository) {
            this.animalRepository = animalRepository;
        }
        public Animal createAnimal(Animal animal) {
            return animalRepository.save(animal);
        }
    public Animal updateAnimal(long id, Animal animal) {
        Animal updatedAnimal = animalRepository.findAnimalById(id);
        if (updatedAnimal == null) {
            return null;
        }
        updatedAnimal.setCatOrDog(animal.getCatOrDog());
        updatedAnimal.setNickName(animal.getNickName());
        updatedAnimal.setBreed(animal.getBreed());
        updatedAnimal.setAge(animal.getAge());
        updatedAnimal.setColor(animal.getColor());
        return animalRepository.save(updatedAnimal);
    }
        public Object getAnimalsPagination(Integer pageNumber, Integer sizeNumber){
            if (pageNumber != null && sizeNumber != null) {
                int startIndex = (pageNumber - 1) * sizeNumber;
                int endIndex = Math.min(startIndex + sizeNumber, animals.size());

                return animals.size();
            } else {
                return animals;
            }
        }


        public List<Animal> getAllAnimals() {

            return animalRepository.findAll();
        }
        public void deleteAnimalById(Long Id) {
            animalRepository.deleteById(Id);
        }
}
