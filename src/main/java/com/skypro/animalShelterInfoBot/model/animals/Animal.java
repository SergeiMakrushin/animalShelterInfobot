package com.skypro.animalShelterInfoBot.model.animals;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
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

    private boolean isVolunteer;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "avatar_id")
     private Avatar avatar;
}
