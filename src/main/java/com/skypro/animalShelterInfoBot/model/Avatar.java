package com.skypro.animalShelterInfoBot.model;

import com.skypro.animalShelterInfoBot.model.Animal;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

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
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    @JdbcTypeCode(Types.LONGVARBINARY)
    private byte[] data;

     @OneToOne
     private Animal animal;
}
