package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
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

@Tag(name = "Контроллер пользователей",
        description = "Создание пользователя.  " +
                "Получение пользователей из базы.  " +
                "Удаление пользователей из базы.  " +
                "Прочие операции.")
@RequestMapping("/user")
@RestController
public class UserController {

    private final InfoBot infoBot;

    public UserController(InfoBot infoBot) {
        this.infoBot = infoBot;
    }

    @Operation(summary = "Получаем пользователей их базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные ползователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ShelterAnimals.class))
                            )
                    )
            })
    @GetMapping
    public ResponseEntity<String> getUsers(
            @Parameter(description = "на сколько элементов отступить, начиная с 1-го, не может быть меньше 1", example = "2")
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(name = "кол-во элементов") Integer sizeNumber) {
//        в лист будет записываться ответ от сервиса
        List<ChatUser> users = new ArrayList<>();
        return ResponseEntity.ok("Раздел в разработке" + users);
    }

    @Operation(summary = "Отправляем сообщение пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ChatUser.class))
                            )
                    )
            })
    @GetMapping("/message_user")
    public ResponseEntity<String> messageUser(
            @RequestParam(name = "chatId пользователя") Long chatId,
            @RequestParam(name = "Текст сообщения") String message) {
        return ResponseEntity.ok(infoBot.sendText(chatId, message));
    }


    @Operation(summary = "Создание пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создать запись о пользователе",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ChatUser.class)
                    )
            )
    )
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody ChatUser chatUser) {
        //        в chatUserResponse будет записываться ответ от сервиса
        ChatUser chatUserResponse = new ChatUser();
        return ResponseEntity.ok("Раздел в разработке" + chatUserResponse);
    }


    @Operation(summary = "Удаляем пользователя из базы данных",
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
    public ResponseEntity<String> deleteUser(@RequestParam(name = "chatId пользователя") Long chatId) {
        //        в chatUserResponse будет записываться ответ от сервиса
        ChatUser chatUserResponse = new ChatUser();
        return ResponseEntity.ok("Раздел в разработке" + chatUserResponse);
    }


    @Operation(summary = "Отключаем рассылку пользователю")
    @PutMapping("/turn_newsletter")
    public ResponseEntity<String> turnOffTheNewsletter(@RequestParam(name = "chatId пользователя") Long chatId) {
        //        в chatUserResponse будет записываться ответ от сервиса
        ChatUser chatUserResponse = new ChatUser();
        return ResponseEntity.ok("Отключение рассылки сообщений для пользователя: "
                + chatUserResponse.getName() + " " + chatUserResponse.getChatId());
    }
}
