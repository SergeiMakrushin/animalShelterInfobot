package com.skypro.animalShelterInfoBot.model.human;

import com.skypro.animalShelterInfoBot.model.animals.Animal;
import jakarta.persistence.*;
import lombok.*;

 /**
 * Модель таблицы, куда будут попадать все первоначально общающиеся с ботом.
 */
@EqualsAndHashCode(exclude = "id")

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_contact_info")
@Entity
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

     /**
     * На первом этапе бот тоже может записать данные пользователя.
     * Поэтому тут тоже можно запросить телефон-емайл.
     */

    @Column(name = "phone")
    private long phoneNumber;

    @Column(name = "email")
    private String email;

    private boolean isVolunteer;
    
}
