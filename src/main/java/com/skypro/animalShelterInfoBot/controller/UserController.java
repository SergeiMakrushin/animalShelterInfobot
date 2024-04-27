package com.skypro.animalShelterInfoBot.controller;


import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.bot.BotServiceImpl;
import com.skypro.animalShelterInfoBot.bot.TelegramBot;
import com.skypro.animalShelterInfoBot.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Tag(name = "Контроллер пользователей",
        description = "Создание пользователя.  " +
                "Получение пользователей из базы.  " +
                "Удаление пользователей из базы.  " +
                "Прочие операции.")
@RequestMapping("/users")
@RestController
public class UserController {

    private final TelegramBot telegramBot;
    private final UserServiceImpl userServiceImpl;
    private final BotServiceImpl botServiceImpl;

    public UserController(TelegramBot telegramBot, UserServiceImpl userServiceImpl, BotServiceImpl botServiceImpl) {
        this.telegramBot = telegramBot;
        this.userServiceImpl = userServiceImpl;
        this.botServiceImpl = botServiceImpl;
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

    @PostMapping("/create")
    public ResponseEntity<ChatUser> createUser(@RequestBody ChatUser user) {
        return ResponseEntity.ok(userServiceImpl.createUser(user));
    }

    @Operation(summary = "Редактирование пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактировать запись о пользователе",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ChatUser.class)
                    )
            )
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ChatUser> editStudent(@PathVariable long id, @RequestBody ChatUser user) {
        ChatUser updatedUser = userServiceImpl.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Получаем всех пользователей из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные ползователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ChatUser.class))
                            )
                    )
            })
    @GetMapping("/all")
    public ResponseEntity<List<ChatUser>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    @Operation(summary = "Получаем пользователей из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные ползователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ChatUser.class))
                            )
                    )
            })

    @GetMapping("/pagination")
    public ResponseEntity<List<ChatUser>> getUsersPagination(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer sizeNumber) {
        return ResponseEntity.ok(userServiceImpl.getUsersPagination(pageNumber, sizeNumber));
    }

    @Operation(summary = "Удаляем пользователя из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ChatUser.class)
                            )
                    )
            })

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userServiceImpl.deleteUserById(userId);
        return ResponseEntity.ok().build();
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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        telegramBot.sendMessage(sendMessage);
        return ResponseEntity.ok(message);
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
