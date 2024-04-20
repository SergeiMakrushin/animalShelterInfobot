package com.skypro.animalShelterInfoBot.services;

import com.skypro.animalShelterInfoBot.model.animals.Animal;
import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import com.skypro.animalShelterInfoBot.repositories.AvatarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarDir;
    private final AnimalService animalService;
    private final AvatarRepository avatarRepository;
    @Autowired
    public AvatarService(AvatarRepository avatarRepository, AnimalService animalService) {
        this.avatarRepository = avatarRepository;
        this.animalService = animalService;
    }
    public void uploadAvatar(Long animalId, MultipartFile file) throws IOException {
        Animal animal = animalService.findAnimal(animalId);
        Path filePath = Path.of(avatarDir, animal.getNickName() + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream io = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(io, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(animalId);
        avatar.setAnimal(animal);
        avatar.setFilePath(filePath.toString());
        avatar.setData(file.getBytes());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));
        avatarRepository.save(avatar);
    }
    public void deleteAvatar(long animalId) {
        avatarRepository.deleteByAnimalId(animalId);
    }
    public Avatar findAvatar(long animalId) {
        return avatarRepository.findByAnimalId(animalId).orElse(new Avatar());
    }
    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int high = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, high, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, high, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
