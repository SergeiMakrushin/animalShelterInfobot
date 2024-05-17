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

    @Lob
  @JdbcTypeCode(Types.LONGVARBINARY)
    private byte[] bytes;

    private Long chatId;
    private String messagePet;
    private String name;
    private String userName;
    private String surname;

    public PetReport() {
    }

    public PetReport(Integer id, byte[] bytes, Long chatId, String messagePet, String name, String userName, String surname) {
        this.id = id;
        this.bytes = bytes;
        this.chatId = chatId;
        this.messagePet = messagePet;
        this.name = name;
        this.userName = userName;
        this.surname = surname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport petReport = (PetReport) o;
        return Objects.equals(id, petReport.id) && Arrays.equals(bytes, petReport.bytes) && Objects.equals(chatId, petReport.chatId) && Objects.equals(messagePet, petReport.messagePet) && Objects.equals(name, petReport.name) && Objects.equals(userName, petReport.userName) && Objects.equals(surname, petReport.surname);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, messagePet, name, userName, surname);
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
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}