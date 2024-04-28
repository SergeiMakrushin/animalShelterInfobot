package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    void deleteAvatarByAnimal_Id(Long animalId);

    Optional<Avatar> findAvatarByAnimal_Id(Long animalId);
}
