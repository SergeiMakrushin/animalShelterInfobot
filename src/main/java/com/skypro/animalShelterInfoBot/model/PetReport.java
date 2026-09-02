package com.skypro.animalShelterInfoBot.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;
/**
 * Сущность Отчета
 */
@Entity
public class PetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long userChatId;

   private String messagePet;
    private String userName;
//    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    public PetReport() {
    }

    public PetReport(Integer id, Long userChatId, String messagePet, String userName, byte[] data) {
        this.id = id;
        this.userChatId = userChatId;
        this.messagePet = messagePet;
        this.userName = userName;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(Long userChatId) {
        this.userChatId = userChatId;
    }

    public String getMessagePet() {
        return messagePet;
    }

    public void setMessagePet(String messagePet) {
        this.messagePet = messagePet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return Objects.equals(id, petReport.id) && Objects.equals(userChatId, petReport.userChatId) && Objects.equals(messagePet, petReport.messagePet) && Objects.equals(userName, petReport.userName) && Arrays.equals(data, petReport.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, userChatId, messagePet, userName);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "PetReport{" +
                "id=" + id +
                ", userChatId=" + userChatId +
                ", messagePet='" + messagePet + '\'' +
                ", userName='" + userName + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
