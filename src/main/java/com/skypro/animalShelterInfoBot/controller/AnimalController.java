package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
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

    private final InfoBot infoBot;
    private final AnimalService animalService;

    public AnimalController(InfoBot infoBot, AnimalService animalService) {
        this.infoBot = infoBot;
        this.animalService = animalService;
    }

    @Operation(summary = "Создание животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создать запись о животном",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterAnimals.class)
                    )
            )
    )
    @PostMapping("/create")
    public ShelterAnimals createAnimal(@RequestBody ShelterAnimals animal) {
        return animalService.createAnimal(animal);
    }

    @Operation(summary = "получаем всех животных из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные животные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ShelterAnimals.class))
                            )
                    )
            })

    @GetMapping("/findAllAnimals")
    public ResponseEntity<Collection<ShelterAnimals>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @Operation(summary = "получаем животных из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные животные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ShelterAnimals.class))
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
                                    schema = @Schema(implementation = ShelterAnimals.class)
                            )
                    )
            })

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Void> deleteAnimalById(@PathVariable Long Id) {
        animalService.deleteAnimalById(Id);
        return ResponseEntity.ok().build();
    }
}