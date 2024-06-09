package com.skypro.animalShelterInfoBot.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InfoBotConfigurationTest {
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    @Test
    public void testBotNameNotNull() {
        assertNotNull(botName);
    }

    @Test
    public void testTokenNotNull() {
        assertNotNull(token);
    }

}