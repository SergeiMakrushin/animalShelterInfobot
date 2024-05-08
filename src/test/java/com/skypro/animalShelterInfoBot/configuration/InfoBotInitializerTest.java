package com.skypro.animalShelterInfoBot.configuration;
import com.skypro.animalShelterInfoBot.bot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
class InfoBotInitializerTest {
    @Test
    public void testInit() {
        TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
        ApplicationContext context = Mockito.mock(ApplicationContext.class);
        ContextRefreshedEvent event = new ContextRefreshedEvent(context);

        InfoBotInitializer infoBotInitializer = new InfoBotInitializer(telegramBot);
        assertDoesNotThrow(() -> infoBotInitializer.init(event));
    }

}