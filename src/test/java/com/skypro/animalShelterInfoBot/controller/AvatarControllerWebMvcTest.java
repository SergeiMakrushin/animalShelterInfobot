package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.model.Avatar;
import com.skypro.animalShelterInfoBot.service.AvatarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AvatarController.class)
public class AvatarControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void testUploadAvatar() throws Exception {
        MockMultipartFile file = new MockMultipartFile("avatar", "test.png", "image/png", "avatar data".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/avatar/{animalId}/avatar", 1)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());

        verify(avatarService).uploadAvatar(eq(1L), any(MultipartFile.class));
    }

    @Test
    public void testDownloadPreview() throws Exception {
        Avatar preview = new Avatar();
        preview.setData("test data".getBytes());
        preview.setMediaType("image/jpeg");
        when(avatarService.findAvatar(anyLong())).thenReturn(preview);

        mockMvc.perform(get("/avatar/avatar-preview/{animalId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(preview.getData()));
    }


    @Test
    public void testDownloadAvatar() throws Exception {
        Avatar avatar = new Avatar();
        avatar.setData("test data".getBytes());
        avatar.setMediaType("image/jpeg");
        when(avatarService.findAvatar(anyLong())).thenReturn(avatar);

        mockMvc.perform(get("/avatar/avatar-from-db/{animalId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(avatar.getData()));
    }

    @Test
    public void testDownloadAvatarFromFile() throws Exception {
        Avatar avatar = new Avatar();
        avatar.setFilePath("path/to/avatar.jpg");
        avatar.setMediaType("image/jpeg");
        avatar.setFileSize(1024L);

        when(avatarService.findAvatar(anyLong())).thenReturn(avatar);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/avatar-from-file/1"))
                .andReturn().getResponse();
    }

    @Test
    public void testDeleteAvatar() throws Exception {
        when(avatarService.findAvatar(1L)).thenReturn(new Avatar());

        mockMvc.perform(delete("/avatar/deleteAvatar/{animalId}", 1))
                .andExpect(status().isOk());

        verify(avatarService).deleteAvatar(1L);
    }
}