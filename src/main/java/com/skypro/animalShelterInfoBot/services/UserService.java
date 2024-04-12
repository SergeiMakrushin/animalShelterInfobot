package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private List<ChatUser> users = new ArrayList<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ChatUser createUser(ChatUser user) {
        return userRepository.save(user);
    }

    public List<ChatUser> getAllUsers() {
        return userRepository.findAll();
    }
    public List<ChatUser> getUsersPagination(Integer pageNumber, Integer sizeNumber) {
        if (pageNumber != null && sizeNumber != null) {
            int startIndex = (pageNumber - 1) * sizeNumber;
            int endIndex = Math.min(startIndex + sizeNumber, users.size());

            users = users.subList(startIndex, endIndex);
        } else {
            users = users;
        }
        return users;
    }
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
