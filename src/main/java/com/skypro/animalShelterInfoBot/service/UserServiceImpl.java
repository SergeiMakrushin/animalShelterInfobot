package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.model.User;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private List<User> users = new ArrayList<>();


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    public User findUserByChatId(long chatId) {
        return userRepository.findUserByChatId(chatId);
    }

    public User updateUser(long id, User user) {
        User updatedUser = userRepository.findUserById(id);
        if (updatedUser == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        updatedUser.setName(user.getName());
        updatedUser.setSurname(user.getSurname());
        updatedUser.setAge(user.getAge());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setAnimals(user.getAnimals());
        return userRepository.save(updatedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersPagination(Integer pageNumber, Integer sizeNumber) {
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

    public List<User> getAllVolunteer() {
        return userRepository.findAllUserByIsVolunteerTrue();
    }
}
