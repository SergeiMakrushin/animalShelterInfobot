package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private List<ChatUser> users = new ArrayList<>();
@Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ChatUser createUser(ChatUser user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }
    public ChatUser updateUser(long id, ChatUser user) {
        ChatUser updatedUser = userRepository.findUserById(id);
        if (updatedUser == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        updatedUser.setName(user.getName());
        updatedUser.setSurname(user.getSurname());
        updatedUser.setAge(user.getAge());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        updatedUser.setEmail(user.getEmail());
        return userRepository.save(updatedUser);
    }
    public List<ChatUser> getAllUsers() {
        return userRepository.findAll();
    }
    public List<ChatUser> getUsersPagination(Integer pageNumber, Integer sizeNumber) {
        if (pageNumber != null && sizeNumber != null) {
            int startIndex = (pageNumber - 1) * sizeNumber;
            int endIndex = Math.min(startIndex + sizeNumber, users.size());

            if (startIndex >= users.size()) {
                throw new IllegalArgumentException("Invalid pageNumber: " + pageNumber);
            }

            users = users.subList(startIndex, endIndex);
        } else {
            users = users;
        }
        return users;
    }
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public List<ChatUser> getAllVolunteer() {
    return userRepository.findAllUserByIsVolunteerTrue();
    }

    public void setUsers(List<ChatUser> userList) {

    }
}
