package com.skypro.animalShelterInfoBot.model.animals;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
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

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "avatar_id", referencedColumnName = "id")
     private Avatar avatar;

     @ManyToOne
     @JoinColumn(name = "chat_user_id", referencedColumnName = "id")
     private ChatUser chatUser;

 }
