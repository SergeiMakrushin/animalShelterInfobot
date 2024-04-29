package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import com.skypro.animalShelterInfoBot.service.AvatarService;
import org.glassfish.jersey.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AvatarController.class)
class AvatarControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvatarService avatarService;

    @Test
    public void testUploadAvatar() throws Exception {
        MockMultipartFile file = new MockMultipartFile("avatar", "test.jpg", "image/jpeg", "test data".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/avatar/1/avatar").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testDownloadPreview() throws Exception {
        when(avatarService.findAvatar(1L)).thenReturn(new Avatar("test data".getBytes(), "image/jpeg"));
        mockMvc.perform(get("/avatar/avatar-preview/1"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "image/jpeg"));
    }

    @Test
    public void testDownloadAvatarFromDb() throws Exception {
        when(avatarService.findAvatar(1L)).thenReturn(new Avatar("test data".getBytes(), "image/jpeg"));
        mockMvc.perform(get("/avatar/avatar-from-db/1"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "image/jpeg"));
    }

    @Test
    public void testDownloadAvatarFromFile() throws Exception {
        when(avatarService.findAvatar(1L)).thenReturn(new Avatar());
        mockMvc.perform(get("/avatar/avatar-from-file/1"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "image/jpeg"));
    }

    @Test
    public void testDeleteAvatar() throws Exception {
        when(avatarService.findAvatar(1L)).thenReturn(new Avatar("test data".getBytes(), "image/jpeg"));
        mockMvc.perform(delete("/avatar/deleteAvatar/1"))
                .andExpect(status().isOk());
    }

}