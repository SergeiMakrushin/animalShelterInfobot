package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    void deleteByAnimalId(long animalId);
    Optional<Avatar> findByAnimalId(Long studentId);
}
