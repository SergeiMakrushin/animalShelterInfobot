package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.bot.BotService;
import com.skypro.animalShelterInfoBot.bot.TelegramBot;
import com.skypro.animalShelterInfoBot.model.User;
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
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.createUser(user);
        assertNotNull(responseEntity);
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testEditUser() {
        long id = 1;
        User user = new User();
        when(userService.updateUser(id, user)).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.editStudent(id, user);
        assertNotNull(responseEntity);
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = List.of(new User(), new User());
        when(userService.getAllUsers()).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();
        assertNotNull(responseEntity);
        assertEquals(userList, responseEntity.getBody());
    }

    @Test
    public void testGetUsersPagination() {
        Integer pageNumber = 1;
        Integer sizeNumber = 10;
        List<User> userList = List.of(new User(), new User());
        when(userService.getUsersPagination(pageNumber, sizeNumber)).thenReturn(userList);
        ResponseEntity<List<User>> responseEntity = userController.getUsersPagination(pageNumber, sizeNumber);
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