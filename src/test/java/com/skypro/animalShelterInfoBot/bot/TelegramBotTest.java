package com.skypro.animalShelterInfoBot.bot;
import com.skypro.animalShelterInfoBot.configuration.InfoBotConfiguration;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
class TelegramBotTest {
    @Test
    public void testGetBotUsername() {
        InfoBotConfiguration config = mock(InfoBotConfiguration.class);
        BotService botService = mock(BotService.class);

        TelegramBot telegramBot = new TelegramBot(config, botService);

        when(config.getBotName()).thenReturn("TestBot");

        String botUsername = telegramBot.getBotUsername();

        assertEquals("TestBot", botUsername);
    }

    @Test
    public void testGetBotToken() {
        InfoBotConfiguration config = mock(InfoBotConfiguration.class);
        BotService botService = mock(BotService.class);

        TelegramBot telegramBot = new TelegramBot(config, botService);

        when(config.getToken()).thenReturn("TestToken");

        String botToken = telegramBot.getBotToken();

        assertEquals("TestToken", botToken);
    }
    @Test
    public void onUpdateReceivedTest() {
        Update update = mock(Update.class);
        TelegramBot telegramBot = new TelegramBot(new InfoBotConfiguration(), mock(BotService.class));
        telegramBot.onUpdateReceived(update);
    }

    @Test
    public void telegramBotConstructorTest() {
        InfoBotConfiguration config = new InfoBotConfiguration();
        BotService botService = mock(BotService.class);
        TelegramBot telegramBot = new TelegramBot(config, botService);
    }

}