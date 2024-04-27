package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<ChatUser, Long> {
    ChatUser findUserById(long id);
    List<ChatUser> findAllUserByIsVolunteerTrue();
}
