package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final UserService userService;

    public UserController(InfoBot infoBot, UserService userService) {
        this.infoBot = infoBot;
        this.userService = userService;
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
    public ChatUser createUser(@RequestBody ChatUser user) {
        return userService.createUser(user);
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
        ChatUser updatedUser = userService.updateUser(id, user);
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
        return ResponseEntity.ok(userService.getAllUsers());
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
        return ResponseEntity.ok(userService.getUsersPagination(pageNumber, sizeNumber));
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
            userService.deleteUserById(userId);
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
        return ResponseEntity.ok(infoBot.sendText(chatId, message));
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
