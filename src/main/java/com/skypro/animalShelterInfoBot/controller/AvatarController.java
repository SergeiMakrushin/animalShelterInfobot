package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import com.skypro.animalShelterInfoBot.services.AvatarService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping(path = "/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{animalId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar
            (@PathVariable long animalId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(animalId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/avatar-preview/{animalId}")
    public ResponseEntity<byte[]> downloadPreview(@PathVariable Long animalId) {
        Avatar preview = avatarService.findAvatar(animalId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(preview.getData().length);
        headers.setContentType(MediaType.parseMediaType(preview.getMediaType()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(preview.getData());
    }

    @GetMapping("/avatar-from-db/{animalId}")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long animalId) {
        Avatar avatar = avatarService.findAvatar(animalId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("avatar-from-file/{animalId}")
    public void downloadAvatar(@PathVariable Long animalId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(animalId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int)avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @DeleteMapping("/deleteAvatar/{animalId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable long animalId) {
        if (avatarService.findAvatar(animalId).getData()!=null) {
            avatarService.deleteAvatar(animalId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();}
}
