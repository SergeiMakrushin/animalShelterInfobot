package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface AvatarService {
    void uploadAvatar(Long animalId, MultipartFile file) throws IOException;
    void deleteAvatar(Long animalId);
    Avatar findAvatar(long animalId);
    byte[] generateImagePreview(Path filePath) throws IOException;
    String getExtension(String filename);
}
