package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.BotService;
import com.skypro.animalShelterInfoBot.bot.TelegramBot;
<<<<<<< HEAD
import com.skypro.animalShelterInfoBot.model.User;
=======
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
>>>>>>> origin/dev
import com.skypro.animalShelterInfoBot.service.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
class UserControllerWebMvcTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private BotService botService;

    public UserControllerWebMvcTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
<<<<<<< HEAD
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.createUser(user);
=======
        ChatUser user = new ChatUser();
        when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<ChatUser> responseEntity = userController.createUser(user);
>>>>>>> origin/dev
        assertNotNull(responseEntity);
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testEditUser() {
        long id = 1;
<<<<<<< HEAD
        User user = new User();
        when(userService.updateUser(id, user)).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.editStudent(id, user);
=======
        ChatUser user = new ChatUser();
        when(userService.updateUser(id, user)).thenReturn(user);
        ResponseEntity<ChatUser> responseEntity = userController.editStudent(id, user);
>>>>>>> origin/dev
        assertNotNull(responseEntity);
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetAllUsers() {
<<<<<<< HEAD
        List<User> userList = List.of(new User(), new User());
        when(userService.getAllUsers()).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();
=======
        List<ChatUser> userList = List.of(new ChatUser(), new ChatUser());
        when(userService.getAllUsers()).thenReturn(userList);
        ResponseEntity<List<ChatUser>> responseEntity = userController.getAllUsers();
>>>>>>> origin/dev
        assertNotNull(responseEntity);
        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    public void testGetUsersPagination() {
        Integer pageNumber = 1;
        Integer sizeNumber = 10;
<<<<<<< HEAD
        List<User> userList = List.of(new User(), new User());
        when(userService.getUsersPagination(pageNumber, sizeNumber)).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userController.getUsersPagination(pageNumber, sizeNumber);
=======
        List<ChatUser> userList = List.of(new ChatUser(), new ChatUser());
        when(userService.getUsersPagination(pageNumber, sizeNumber)).thenReturn(userList);
        ResponseEntity<List<ChatUser>> responseEntity = userController.getUsersPagination(pageNumber, sizeNumber);
>>>>>>> origin/dev
        assertNotNull(responseEntity);
        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    public void testDeleteUserById() {
        Long userId = 1L;
        ResponseEntity<Void> responseEntity = userController.deleteUserById(userId);
        assertNotNull(responseEntity);
    }
}