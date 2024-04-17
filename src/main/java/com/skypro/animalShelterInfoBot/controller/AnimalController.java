package com.skypro.animalShelterInfoBot.controller;


import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import com.skypro.animalShelterInfoBot.service.bot.TelegramBot;
import com.skypro.animalShelterInfoBot.services.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
                            schema = @Schema(implementation = ShelterAnimals.class)
                    )
            )
    )
    @PostMapping
    public ResponseEntity<String> createAnimal(@RequestBody ShelterAnimals animals) {
        //        в shelterAnimals будет записываться ответ от сервиса
        ShelterAnimals shelterAnimals = new ShelterAnimals(); // нет метода create
        return ResponseEntity.ok("Animal created successfully" + shelterAnimals);
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
    @GetMapping
    public ResponseEntity<String> getAllAnimals(@RequestBody ShelterAnimals animals) {
//        в animalsList будет записываться ответ от сервиса
        List<ShelterAnimals> animalsList = new ArrayList<>(); // здесь должен быть getAllAnimals
        return ResponseEntity.ok("All animals" + animalsList);
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
    public ResponseEntity<List<ShelterAnimals>> getAnimalPagination(@Parameter(description = "на сколько элементов отступить, начиная с 1-го, не может быть меньше 1", example = "2")
                                                                    @RequestParam(value = "page", required = false) Integer pageNumber,
                                                                    @RequestParam(name = "кол-во элементов") Integer sizeNumber) {
        List<ShelterAnimals> paginatedList = new ArrayList<>(); // метод getAnimalsPagination

        return ResponseEntity.ok(paginatedList);
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
    @DeleteMapping("/{nickname}")
    public ResponseEntity<String> deleteAnimalByNickname(@PathVariable String nickname) {
        animalService.deleteAnimalByNickname(nickname);
        return ResponseEntity.ok("Animal with nickname " + nickname + " deleted");
    }
}
