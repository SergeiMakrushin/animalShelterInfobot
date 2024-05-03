package com.skypro.animalShelterInfoBot.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * модель животных.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Animal {


    public Animal(Long id, TapeOfAnimal catOrDog, String nickName, String breed, float age, String color) {
        this.id = id;
        this.catOrDog = catOrDog;
        this.nickName = nickName;
        this.breed = breed;
        this.age = age;
        this.color = color;
    }


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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Avatar avatar;

    @ManyToOne
    @JoinColumn(name = "chat_user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Кличка " + nickName +
                ", Порода " + breed +
                ", Возраст " + age +
                ", Цвет шёрстки " + color;
    }
}
