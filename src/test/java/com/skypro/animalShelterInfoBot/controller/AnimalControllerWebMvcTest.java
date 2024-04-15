package com.skypro.animalShelterInfoBot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.animalShelterInfoBot.bot.InfoBot;
import com.skypro.animalShelterInfoBot.model.animals.Animal;
import com.skypro.animalShelterInfoBot.services.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnimalController.class)
class AnimalControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @MockBean
    private InfoBot infoBot;

    @Test
    public void testCreateAnimal() throws Exception {
        Animal animal = new Animal();
        given(animalService.createAnimal(any(Animal.class))).willReturn(animal);

        mockMvc.perform(post("/animals/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animal)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAnimal() throws Exception {
        Animal animal = new Animal();
        given(animalService.updateAnimal(anyLong(), any(Animal.class))).willReturn(animal);

        mockMvc.perform(put("/animals/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animal)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllAnimals() throws Exception {
        List<Animal> animals = Arrays.asList(new Animal(), new Animal());
        given(animalService.getAllAnimals()).willReturn(animals);

        mockMvc.perform(get("/animals/findAllAnimals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAnimalsPagination() throws Exception {
        given(animalService.getAnimalsPagination(anyInt(), anyInt())).willReturn(new ArrayList<>());

        mockMvc.perform(get("/animals/pagination?pageNumber=1&sizeNumber=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testDeleteAnimalById() throws Exception {
        mockMvc.perform(delete("/animals/delete/1"))
                .andExpect(status().isOk());

        verify(animalService).deleteAnimalById(1L);
    }

}