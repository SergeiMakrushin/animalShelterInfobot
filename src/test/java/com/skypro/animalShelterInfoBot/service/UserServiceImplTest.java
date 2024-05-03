package com.skypro.animalShelterInfoBot.service;
import com.skypro.animalShelterInfoBot.model.User;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
        User user = new User();
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user, createdUser);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setChatId(11111111L);
        existingUser.setName("John");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setChatId(11111111L);
        updatedUser.setName("Doe");

        Mockito.when(userRepository.findUserByChatId(11111111L)).thenReturn(existingUser);
        Mockito.when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(11111111L, updatedUser);

        assertEquals(updatedUser.getName(), result.getName());
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUserById(userId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void testGetAllVolunteer() {
        List<User> volunteerList = new ArrayList<>();
        volunteerList.add(new User());
        volunteerList.add(new User());

        Mockito.when(userRepository.findAllUserByIsVolunteerTrue()).thenReturn(volunteerList);

        List<User> result = userService.getAllVolunteer();

        assertEquals(2, result.size());
    }

    @Test
    void testCreateUserWithNullUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
    }

    @Test
    void testUpdateUserWithInvalidId() {
        User updatedUser = new User();
        updatedUser.setId(100L);

        Mockito.when(userRepository.findUserById(100L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(100L, updatedUser));
    }

    @Test
    void testGetUsersPaginationWithInvalidPageNumber() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        assertThrows(IllegalArgumentException.class, () -> userService.getUsersPagination(2, 1));
    }

}