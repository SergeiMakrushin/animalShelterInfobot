package com.skypro.animalShelterInfoBot.model.animals;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(exclude = "id", callSuper = true)
@SuperBuilder
@Getter
@NoArgsConstructor
@Table(name = "Dog")
@Entity
public class Dogs extends ShelterAnimals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Dogs(String nickName, String breed, float age, String color, Long id) {
        super(nickName, breed, age, color);
        this.id = id;
    }
}

