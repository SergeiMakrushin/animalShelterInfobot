package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    List<User> findAllUserByIsVolunteerTrue();

    User findUserByChatId(long id);
}
