package com.skypro.animalShelterInfoBot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvatarTest {
    @Test
    void testAvatarId() {
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        assertEquals(1L, avatar.getId());
    }

    @Test
    void testAvatarFilePath() {
        Avatar avatar = new Avatar();
        avatar.setFilePath("/path/to/avatar");
        assertEquals("/path/to/avatar", avatar.getFilePath());
    }

    @Test
    void testAvatarFileSize() {
        Avatar avatar = new Avatar();
        avatar.setFileSize(1024);
        assertEquals(1024, avatar.getFileSize());
    }

    @Test
    void testAvatarMediaType() {
        Avatar avatar = new Avatar();
        avatar.setMediaType("image/jpeg");
        assertEquals("image/jpeg", avatar.getMediaType());
    }

    @Test
    void testAvatarData() {
        Avatar avatar = new Avatar();
        byte[] data = {1, 0, 1};
        avatar.setData(data);
        assertEquals(data, avatar.getData());
    }

    @Test
    void testAvatarAnimal() {
        Avatar avatar = new Avatar();
        Animal animal = new Animal();
        avatar.setAnimal(animal);
        assertEquals(animal, avatar.getAnimal());
    }

}