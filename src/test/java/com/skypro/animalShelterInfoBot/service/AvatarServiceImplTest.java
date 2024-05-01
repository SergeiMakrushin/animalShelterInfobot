package com.skypro.animalShelterInfoBot.service;
import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.model.Avatar;
import com.skypro.animalShelterInfoBot.repositories.AvatarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {

    @Mock
    private AvatarRepository avatarRepository;

    @Mock
    private AnimalServiceImpl animalServiceImpl;

    @InjectMocks
    private AvatarServiceImpl avatarService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadAvatar() throws IOException {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setNickName("testAnimal");

        MultipartFile file = new MockMultipartFile("test.jpg", new byte[10]);

        when(animalServiceImpl.findAnimal(1L)).thenReturn(animal);

        avatarService.uploadAvatar(1L, file);

        verify(avatarRepository, times(1)).save(any(Avatar.class));
    }

    @Test
    public void testDeleteAvatar() {
        avatarService.deleteAvatar(1L);

        verify(avatarRepository, times(1)).deleteAvatarByAnimal_Id(1L);
    }

    @Test
    public void testFindAvatar() {
        Avatar avatar = new Avatar();
        avatar.setAnimal(new Animal());
        avatar.setFilePath("test.jpg");

        when(avatarRepository.findAvatarByAnimal_Id(1L)).thenReturn(Optional.of(avatar));

        Avatar foundAvatar = avatarService.findAvatar(1L);

        assertEquals(avatar, foundAvatar);
    }

    @Test
    public void testGetExtension() {
        String filename = "test.jpg";

        String extension = avatarService.getExtension(filename);

        assertEquals("jpg", extension);
    }
}