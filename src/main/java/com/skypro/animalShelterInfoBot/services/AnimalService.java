package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {
    private List<ShelterAnimals> animals = new ArrayList<>();

    public ShelterAnimals createAnimal(ShelterAnimals animal) {
         /**
         * Проверка наличия животного.
         */
        if (animal == null) {
            throw new IllegalArgumentException("An animal can't be empty");
        }

         /**
         * Проверка наличия клички у животного.
         */
        if (animal.getNickName() == null || animal.getNickName().isEmpty()) {
            throw new IllegalArgumentException("The animal's nickname is not specified");
        }

         /**
         * Проверка наличия такого животного в базе
         */
        for (ShelterAnimals existingAnimal : animals) {
            if (existingAnimal.getNickName().equals(animal.getNickName())) {
                throw new IllegalArgumentException("An animal with the same nickname already exists");
            }
        }

        animals.add(animal);
        return animal;
    }

    public List<ShelterAnimals> getAllAnimals() {
        if (animals.isEmpty()) {
            throw new IllegalArgumentException("No animals found in the database");
        } else {
            return animals;
        }
    }

    public void deleteAnimalByNickname(String nickname) {
        boolean found = false;
        for (ShelterAnimals animal : animals) {
            if (animal.getNickName().equals(nickname)) {
                animals.remove(animal);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Animal with nickname " + nickname + " not found");
        }

    }
}
