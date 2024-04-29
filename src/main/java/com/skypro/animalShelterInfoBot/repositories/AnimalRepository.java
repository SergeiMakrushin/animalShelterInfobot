package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
   Animal findAnimalById(long id);
}
