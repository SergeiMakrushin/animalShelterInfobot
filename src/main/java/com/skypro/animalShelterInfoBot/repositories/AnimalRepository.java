package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<ShelterAnimals, Long> {
}
