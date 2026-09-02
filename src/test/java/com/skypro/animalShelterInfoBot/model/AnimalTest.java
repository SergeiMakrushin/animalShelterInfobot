package com.skypro.animalShelterInfoBot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    public void testAnimalConstructor() {
        Animal animal = new Animal(1L, Animal.TapeOfAnimal.DOG, "Buddy", "Labrador Retriever", 2.5f, "Golden");

        assertEquals(1L, animal.getId());
        assertEquals(Animal.TapeOfAnimal.DOG, animal.getCatOrDog());
        assertEquals("Buddy", animal.getNickName());
        assertEquals("Labrador Retriever", animal.getBreed());
        assertEquals(2.5f, animal.getAge());
        assertEquals("Golden", animal.getColor());
    }
    @Test
    public void testAnimalToString() {
        Animal animal = new Animal();
        animal.setNickName("Simba");
        animal.setBreed("Maine Coon");
        animal.setAge(3.5f);
        animal.setColor("Ginger");

        String expected = "Кличка: Simba\n" +
                "Порода Maine Coon\n" +
                "Возраст 3.5\n" +
                "Цвет шёрстки Ginger\n\n";

        assertEquals(expected, animal.toString());
    }

}