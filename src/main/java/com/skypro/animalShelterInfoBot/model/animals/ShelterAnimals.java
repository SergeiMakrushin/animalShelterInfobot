package com.skypro.animalShelterInfoBot.model.animals;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
abstract class ShelterAnimals {
    @Column(name = "name")
    private String nickName;  //кличка
    @Column(name = "breed")
    private String breed;     //порода
    @Column(name = "age")
    private float age;        //возраст
    @Column(name = "color")
    private String color;     //окрас
}
