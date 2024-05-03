package com.skypro.animalShelterInfoBot.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Integer age;

     /**
     * На первом этапе бот тоже может записать данные пользователя.
     * Поэтому тут тоже можно запросить телефон-емайл.
     */

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    private Boolean isVolunteer;

    @OneToMany(mappedBy = "user")
    private List<Animal> animals;

}
