package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // зачем нам третий репозиторий?
public interface BotRepository extends JpaRepository <ChatUser, Integer> {
}
