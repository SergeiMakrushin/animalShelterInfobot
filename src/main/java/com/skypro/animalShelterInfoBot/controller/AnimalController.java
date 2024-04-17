package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.Animal;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;


import com.skypro.animalShelterInfoBot.service.bot.TelegramBot;
import com.skypro.animalShelterInfoBot.services.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Tag(name = "Контроллер животных",
        description = "Создание животного.  " +
                "Получение животных из базы.  " +
                "Удаление животных из базы.")
@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final TelegramBot telegramBot;
    private final AnimalService animalService;

    public AnimalController( TelegramBot telegramBot,AnimalService animalService) {
        this.telegramBot = telegramBot;
        this.animalService = animalService;
    }

    @Operation(summary = "Создание животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создать запись о животном",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            )
    )
    @PostMapping("/create")
    public ResponseEntity <Animal> createAnimal(@RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.createAnimal(animal));
    }
    @Operation(summary = "Редактирование животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактировать запись о животном",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Animal.class)
                    )
            )
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Animal> editStudent(@PathVariable long id, @RequestBody Animal animal) {
        Animal updatedAnimal = animalService.updateAnimal(id, animal);
        if (updatedAnimal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAnimal);
    }

    @Operation(summary = "получаем всех животных из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные животные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                            )
                    )
            })

    @GetMapping("/findAllAnimals")
    public ResponseEntity<Collection<Animal>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @Operation(summary = "получаем животных из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные животные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                            )
                    )
            })

    @GetMapping("/pagination")
    public ResponseEntity<Object> getAnimalsPagination(@RequestParam Integer pageNumber, @RequestParam Integer sizeNumber) {
        return ResponseEntity.ok(animalService.getAnimalsPagination(pageNumber, sizeNumber));
    }

    @Operation(summary = "Удаляем животное из базы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class)
                            )
                    )
            })

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Void> deleteAnimalById(@PathVariable Long Id) {
        animalService.deleteAnimalById(Id);
        return ResponseEntity.ok().build();
    }
}
