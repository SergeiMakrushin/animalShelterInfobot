package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
   Animal findAnimalById(long id);

   @Query("SELECT a FROM Animal a WHERE  a.catOrDog=:catOrDog")
   List<Animal> findByCatOrDog(Animal.TapeOfAnimal catOrDog);

   List<Animal> findAllByColorContainingIgnoreCase(String color);
}
