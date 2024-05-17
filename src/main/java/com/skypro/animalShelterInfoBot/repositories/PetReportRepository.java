package com.skypro.animalShelterInfoBot.repositories;

import com.skypro.animalShelterInfoBot.model.PetReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetReportRepository extends JpaRepository <PetReport,Integer> {
}
