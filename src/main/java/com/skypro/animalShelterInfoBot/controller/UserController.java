package com.skypro.animalShelterInfoBot.controller;


import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.service.bot.BotService;
import com.skypro.animalShelterInfoBot.service.bot.TelegramBot;
import com.skypro.animalShelterInfoBot.services.UserService;
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

//    private final TelegramBot telegramBot;
    private final UserService userService;
//    private final BotService botService;

    public UserController( UserService userService) {
//        this.telegramBot = telegramBot;TelegramBot telegramBot,
        this.userService = userService;
//        this.botService = botService;, BotService botService
    }

    @Operation(summary = "Получаем пользователей из базы данных",
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
    @GetMapping("/pagination")
    public ResponseEntity<List<ChatUser>> getUsersPagination(
            @Parameter(description = "на сколько элементов отступить, начиная с 1-го, не может быть меньше 1", example = "2")
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(name = "кол-во элементов") Integer sizeNumber) {
//        в лист будет записываться ответ от сервиса
        List<ChatUser> paginatedUsersList = new ArrayList<>();
        return ResponseEntity.ok(paginatedUsersList);
    }

    @Operation(summary = "Получаем всех пользователей из базы данных",
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
    public ResponseEntity<String> getAllUsers(@RequestBody ChatUser users) {
//        в лист будет записываться ответ от сервиса
        List<ChatUser> chatUsersList = new ArrayList<>();
        return ResponseEntity.ok("All animals" + chatUsersList);
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
//        telegramBot.sendMessage(botService.settingSendMessage(chatId, message));
        return ResponseEntity.ok(message);
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
        return ResponseEntity.ok("User created successfully" + chatUserResponse);
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
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with id " + userId + " deleted");
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
