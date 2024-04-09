package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.animals.ShelterAnimals;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
        private List<ChatUser> users = new ArrayList<>();

        public ChatUser createUser(ChatUser user) {
             /**
             * проверка наличие пользователя.
             */
            if (user == null) {
                throw new IllegalArgumentException("User cannot be null");
            }

             /**
             * проверка наличия такого пользовотеля в базе.
             */
            for (ChatUser existingUser : users) {
                if (existingUser.getSurname().equals(user.getSurname())) {
                    throw new IllegalArgumentException("User with this username already exists");
                }
            }

             /**
             * проверка существует ли пользователь с таким адресом электронной почты.
             */
            for (ChatUser existingUser : users) {
                if (existingUser.getEmail().equals(user.getEmail())) {
                    throw new IllegalArgumentException("User with this email already exists");
                }
            }
            users.add(user);
            return user;
        }

        public List<ChatUser> getAllUsers() {
            if (users.isEmpty()) {
                throw new IllegalStateException("No users found in the database");
            } else {
                return users;
            }
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
            boolean found = false;
            for (ChatUser user : users) {
                if (user.getId().equals(userId)) {
                    users.remove(user);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("User with id " + userId + " not found");
            }
        }

    }
