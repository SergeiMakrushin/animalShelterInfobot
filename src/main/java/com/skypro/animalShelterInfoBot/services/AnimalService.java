package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import com.skypro.animalShelterInfoBot.repositories.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private Collection<ShelterAnimals> animals;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
    public ShelterAnimals createAnimal(ShelterAnimals animal) {
        return animalRepository.save(animal);
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


    public List<ShelterAnimals> getAllAnimals() {
        return animalRepository.findAll();
    }

    public void deleteAnimalByNickname(String nickname) {
        boolean found = false;
        for (ShelterAnimals animal : getAllAnimals()) {
            if (animal.getNickName().equals(nickname)) {
                getAllAnimals().remove(animal);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Animal with nickname " + nickname + " not found");
        }

    }
}
