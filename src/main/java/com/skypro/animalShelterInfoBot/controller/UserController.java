package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private InfoBot infoBot;


    //  получить пользователей
    @GetMapping
    public ResponseEntity<String> getUsers(@RequestParam(value = "page", required = false) Integer pageNumber,
                                           @RequestParam("size") Integer sizeNumber) {
//        в лист будет записываться ответ от сервиса
        List<ChatUser> users = new ArrayList<>();
        return ResponseEntity.ok("Раздел в разработке" + users);
    }

    // отправить сообщение пользователю
    @GetMapping("/message_user")
    public ResponseEntity<String> messageUser(@RequestParam("id") Long chatId, @RequestParam String message) {
        return ResponseEntity.ok(infoBot.sendText(chatId, message));
    }

    // создать пользователя
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody ChatUser chatUser) {
        //        в chatUserResponse будет записываться ответ от сервиса
        ChatUser chatUserResponse = new ChatUser();
        return ResponseEntity.ok("Раздел в разработке" + chatUserResponse);
    }

    // удалить пользователя
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam("id") Long chatId) {
        //        в chatUserResponse будет записываться ответ от сервиса
        ChatUser chatUserResponse = new ChatUser();
        return ResponseEntity.ok("Раздел в разработке" + chatUserResponse);
    }

// отключить рассылку

    @PutMapping("/turn_newsletter")
    public ResponseEntity<String> turnOffTheNewsletter(@RequestParam("id") Long chatId) {
        //        в chatUserResponse будет записываться ответ от сервиса
        ChatUser chatUserResponse = new ChatUser();
        return ResponseEntity.ok("Отключение рассылки сообщений для пользователя: "
                + chatUserResponse.getName() + " " + chatUserResponse.getChatId());
    }
}
