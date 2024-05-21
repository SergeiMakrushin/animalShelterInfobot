package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.model.Avatar;
import com.skypro.animalShelterInfoBot.service.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "Контроллер аватара",
        description = "Загрузки аватара. " +
                "Скачивание предпросмотра аватара. " +
                "Скачивание аватара из базы данных. " +
                "Скачивание аватара из файла. " +
                "Удаление аватара.")
@RestController
@RequestMapping(path = "/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }
    @Operation(summary = "Загрузка аватара",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Загрузить аватар",

                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
//                            schema = @Schema(implementation = Avatar.class)
                    )
            )
    )


    @PostMapping(value = "/{animalId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long animalId, @RequestPart MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(animalId, avatar);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Скачиваем для предпросмотра аватара",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные аватары",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Avatar.class))
                            )
                    )
            })
    @GetMapping("/avatar-preview/{animalId}")
    public ResponseEntity<byte[]> downloadPreview(@PathVariable Long animalId) {
        Avatar preview = avatarService.findAvatar(animalId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(preview.getData().length);
        headers.setContentType(MediaType.parseMediaType(preview.getMediaType()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(preview.getData());
    }
    @Operation(summary = "Скачиваем аватары из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные аватары",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Avatar.class))
                            )
                    )
            })
    @GetMapping("/avatar-from-db/{animalId}")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long animalId) {
        Avatar avatar = avatarService.findAvatar(animalId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }
    @Operation(summary = "Скачиваем аватары из файла",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные аватары",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Avatar.class))
                            )
                    )
            })
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
    @Operation(summary = "Удаляем аватар",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Avatar.class)
                            )
                    )
            })
    @DeleteMapping("/deleteAvatar/{animalId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable Long animalId) {
        if (avatarService.findAvatar(animalId) != null) {
            avatarService.deleteAvatar(animalId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();}
}
