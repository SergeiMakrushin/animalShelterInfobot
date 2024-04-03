package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private InfoBot infoBot;


    @Operation(summary = "Создание животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создать запись о животном",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterAnimals.class)
                    )
            )
    )
    @PostMapping
    public ResponseEntity<String> createAnimal(@RequestBody ShelterAnimals animals) {
        //        в shelterAnimals будет записываться ответ от сервиса
        ShelterAnimals shelterAnimals = new ShelterAnimals();
        return ResponseEntity.ok("Раздел в разработке" + shelterAnimals);
    }


    @Operation(summary = "получаем животных их базы данных",
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
    @GetMapping
    public ResponseEntity<String> getAnimal(@Parameter(description = "на сколько элементов отступить, начиная с 1-го, не может быть меньше 1", example = "2")
                                            @RequestParam(value = "page", required = false) Integer pageNumber,
                                            @RequestParam(name = "кол-во элементов") Integer sizeNumber) {
//        в animalsList будет записываться ответ от сервиса
        List<ShelterAnimals> animalsList = new ArrayList<>();
        return ResponseEntity.ok("Раздел в разработке" + animalsList);
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
    @DeleteMapping
    public ResponseEntity<String> deleteAnimal(@RequestParam(name = "Кличка животного") String nickName) {
        //        в animal будет записываться ответ от сервиса
        ShelterAnimals animal = new ShelterAnimals();
        return ResponseEntity.ok("Раздел в разработке" + animal);
    }
}
