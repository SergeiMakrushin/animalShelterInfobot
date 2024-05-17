package com.skypro.animalShelterInfoBot.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class PetReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] bytes;

    private Long chatId;
    private String messagePet;

    public PetReport() {
    }

    public PetReport(Integer id, byte[] bytes, Long chatId, String messagePet) {
        this.id = id;
        this.bytes = bytes;
        this.chatId = chatId;
        this.messagePet = messagePet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessagePet() {
        return messagePet;
    }

    public void setMessagePet(String messagePet) {
        this.messagePet = messagePet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport petReport = (PetReport) o;
        return Objects.equals(id, petReport.id) && Arrays.equals(bytes, petReport.bytes) && Objects.equals(chatId, petReport.chatId) && Objects.equals(messagePet, petReport.messagePet);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, messagePet);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "PetReport{" +
                "id=" + id +
                ", bytes=" + Arrays.toString(bytes) +
                ", chatId=" + chatId +
                ", messagePet='" + messagePet + '\'' +
                '}';
    }
}
