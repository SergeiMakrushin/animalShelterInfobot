package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.human.ChatUser;

import java.util.List;

public interface UserService {
    ChatUser createUser(ChatUser user);
    ChatUser updateUser(long id, ChatUser user);
    List<ChatUser> getAllUsers();
    List<ChatUser> getUsersPagination(Integer pageNumber, Integer sizeNumber);
    void deleteUserById(Long userId);
    List<ChatUser> getAllVolunteer();
}
