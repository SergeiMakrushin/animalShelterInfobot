package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private InfoBot infoBot;


    // Создать запись о животном
    @PostMapping
    public ResponseEntity<String> createAnimal(@RequestBody ShelterAnimals animals) {
        //        в shelterAnimals будет записываться ответ от сервиса
        ShelterAnimals shelterAnimals = new ShelterAnimals();
        return ResponseEntity.ok("Раздел в разработке" + shelterAnimals);
    }

    // Получить всех животных
    @GetMapping
    public ResponseEntity<String> getAnimal(@RequestParam(value = "page", required = false) Integer pageNumber,
                                            @RequestParam("size") Integer sizeNumber) {
//        в animalsList будет записываться ответ от сервиса
        List<ShelterAnimals> animalsList = new ArrayList<>();
        return ResponseEntity.ok("Раздел в разработке" + animalsList);
    }

    // Удалить животное
    @DeleteMapping
    public ResponseEntity<String> deleteAnimal(@RequestParam("name") String nickName) {
        //        в animal будет записываться ответ от сервиса
        ShelterAnimals animal = new ShelterAnimals();
        return ResponseEntity.ok("Раздел в разработке" + animal);
    }
}
