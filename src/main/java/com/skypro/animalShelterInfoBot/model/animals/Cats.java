package com.skypro.animalShelterInfoBot.model.animals;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(exclude = "id", callSuper = true)
@SuperBuilder
@Getter
@NoArgsConstructor
@Table(name = "Cat")
@Entity
public class Cats extends ShelterAnimals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Cats(String nickName, String breed, float age, String color, Long id) {
        super(nickName, breed, age, color);
        this.id = id;
    }
}
