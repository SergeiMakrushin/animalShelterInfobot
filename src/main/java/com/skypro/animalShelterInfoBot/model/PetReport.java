package com.skypro.animalShelterInfoBot.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class PetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long chatId;
//    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    public PetReport() {
    }

    public PetReport(Integer id, Long chatId, byte[] data) {
        this.id = id;
        this.chatId = chatId;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport petReport = (PetReport) o;
        return Objects.equals(id, petReport.id) && Objects.equals(chatId, petReport.chatId) && Arrays.equals(data, petReport.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "PetReport{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
