package com.skypro.animalShelterInfoBot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getChatId());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getAge());
        assertNull(user.getPhoneNumber());
        assertNull(user.getEmail());
        assertNull(user.getIsVolunteer());
        assertNull(user.getAnimals());
    }

    @Test
    void testUserSettersAndGetters() {
        User user = new User();
        Long id = 1L;
        Long chatId = 123456L;

        user.setId(id);
        user.setChatId(chatId);
        assertEquals(id, user.getId());
        assertEquals(chatId, user.getChatId());
    }

    @Test
    void testUserEqualsAndHashCode() {
        User user1 = new User();
        User user2 = new User();
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

}