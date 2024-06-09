package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(long id, User user);
    List<User> getAllUsers();
    List<User> getUsersPagination(Integer pageNumber, Integer sizeNumber);
    void deleteUserById(Long userId);
    List<User> getAllVolunteer();
}
