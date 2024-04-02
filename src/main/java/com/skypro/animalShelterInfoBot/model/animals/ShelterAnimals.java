package com.skypro.animalShelterInfoBot.model.animals;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity

public class ShelterAnimals {
    public enum TapeOfAnimal {DOG, CAT}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private TapeOfAnimal catOrDog; //вид животного
    @Column(name = "name")
    private String nickName;  //кличка

    private String breed;     //порода

    private float age;        //возраст

    private String color;     //окрас


}
