package com.skypro.animalShelterInfoBot.model.avatar;

import com.skypro.animalShelterInfoBot.model.animals.Animal;
import jakarta.persistence.*;
import lombok.*;

 /**
 * Сущность Аватар
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avatar { // переложить сущность в пакет model
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

     public void setAnimal(Animal animal) {
     }
 }
