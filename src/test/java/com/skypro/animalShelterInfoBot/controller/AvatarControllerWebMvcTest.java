package com.skypro.animalShelterInfoBot.controller;

import com.skypro.animalShelterInfoBot.model.avatar.Avatar;
import com.skypro.animalShelterInfoBot.services.AvatarService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        MockMultipartFile file = new MockMultipartFile("avatar", "test.png",
                MediaType.IMAGE_PNG_VALUE, "image data".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/avatar/1/avatar").file(file))
                .andExpect(status().isOk());

        verify(avatarService, times(1)).uploadAvatar(eq(1L), any(MultipartFile.class));
    }

    @Test
    public void testDownloadPreview() throws Exception {
        when(avatarService.findAvatar(anyLong())).thenReturn(new Avatar("preview data".getBytes(), "image/png"));

        MvcResult result = mockMvc.perform(get("/avatar/avatar-preview/1"))
                .andExpect(status().isOk())
                .andReturn();

        byte[] content = result.getResponse().getContentAsByteArray();
        assertEquals("preview data", new String(content));
    }

    @Test
    public void testDownloadAvatar() throws Exception {
        when(avatarService.findAvatar(anyLong())).thenReturn(new Avatar("avatar data".getBytes(), "image/jpeg"));

        MvcResult result = mockMvc.perform(get("/avatar/avatar-from-db/1"))
                .andExpect(status().isOk())
                .andReturn();

        byte[] content = result.getResponse().getContentAsByteArray();
        assertEquals("avatar data", new String(content));
    }

    @Test
    public void testDownloadAvatarFromFile() throws Exception {
        Avatar avatar = new Avatar("avatar data".getBytes(), "image/jpeg");
        avatar.setFilePath("/path/to/file.jpg");

        when(avatarService.findAvatar(anyLong())).thenReturn(avatar);

        MockHttpServletResponse response = mockMvc.perform(get("/avatar/avatar-from-file/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] content = response.getContentAsByteArray();
        assertEquals("avatar data", new String(content));
        assertEquals("image/jpeg", response.getContentType());
    }

    @Test
    public void testDeleteAvatar() throws Exception {
        when(avatarService.findAvatar(anyLong())).thenReturn(new Avatar());

        mockMvc.perform(delete("/avatar/deleteAvatar/1"))
                .andExpect(status().isOk());

        verify(avatarService, times(1)).deleteAvatar(1L);
    }

}