package com.skypro.animalShelterInfoBot.service;

import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.model.Avatar;
import com.skypro.animalShelterInfoBot.repositories.AvatarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    @Mock
    private AvatarServiceImpl avatarService;

    @InjectMocks
    private AvatarServiceImpl avatarService1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadAvatar() throws IOException {

        Animal animal = new Animal(1l, Animal.TapeOfAnimal.DOG, "Buddy", "Golden Retriever", 5, "Golden");

        MultipartFile file = new MockMultipartFile("test.jpg", new byte[10]);
        when(animalServiceImpl.findAnimal(any(Long.class))).thenReturn(animal);

        ArgumentCaptor<MultipartFile> valueCapture = ArgumentCaptor.forClass(MultipartFile.class);
        doNothing().when(avatarService).uploadAvatar(any(Long.class), valueCapture.capture());
        avatarService.uploadAvatar(1l, file);

        assertEquals(file, valueCapture.getValue());

    }

    @Test
    public void testDeleteAvatar() {
        avatarService1.deleteAvatar(1L);

        verify(avatarRepository, times(1)).deleteAvatarByAnimal_Id(1L);
    }

    @Test
    public void testFindAvatar() {
        Avatar avatar = new Avatar();
        avatar.setAnimal(new Animal());
        avatar.setFilePath("test.jpg");

        when(avatarRepository.findAvatarByAnimal_Id(1L)).thenReturn(Optional.of(avatar));

        Avatar foundAvatar = avatarService1.findAvatar(1L);

        assertEquals(avatar, foundAvatar);
    }

    @Test
    public void testGetExtension() {
        String filename = "test.jpg";

        String extension = avatarService1.getExtension(filename);

        assertEquals("jpg", extension);
    }
}