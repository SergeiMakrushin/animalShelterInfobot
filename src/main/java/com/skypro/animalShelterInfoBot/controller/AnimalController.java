package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
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

    @PostMapping
    public ShelterAnimals creatAnimal(@RequestBody ShelterAnimals animal) {
        return animalService.createAnimal(animal);
    }
//    @PostMapping
//    public ResponseEntity<String> createAnimal(@RequestBody ShelterAnimals animals) {
//        //        в shelterAnimals будет записываться ответ от сервиса
//        ShelterAnimals shelterAnimals = new ShelterAnimals();
//        return ResponseEntity.ok("Раздел в разработке" + shelterAnimals);
//    }


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
    @GetMapping
    public List<ShelterAnimals> getAllAnimals() {
        return animalService.getAllAnimals();
    }
//    @GetMapping
//    public ResponseEntity<String> getAnimal(@Parameter(description = "на сколько элементов отступить, начиная с 1-го, не может быть меньше 1", example = "2")
//                                            @RequestParam(value = "page", required = false) Integer pageNumber,
//                                            @RequestParam(name = "кол-во элементов") Integer sizeNumber) {
////        в animalsList будет записываться ответ от сервиса
//        List<ShelterAnimals> animalsList = new ArrayList<>();
//        return ResponseEntity.ok("Раздел в разработке" + animalsList);
//    }


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
    public void deleteAnimal(@PathVariable String nickname) {
        animalService.deleteAnimalByNickname(nickname);
    }
//    @DeleteMapping
//    public ResponseEntity<String> deleteAnimal(@RequestParam(name = "Кличка животного") String nickName) {
//        //        в animal будет записываться ответ от сервиса
//        ShelterAnimals animal = new ShelterAnimals();
//        return ResponseEntity.ok("Раздел в разработке" + animal);
//    }
}
