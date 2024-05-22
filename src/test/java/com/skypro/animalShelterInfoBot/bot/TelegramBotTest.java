package com.skypro.animalShelterInfoBot.bot;
import com.skypro.animalShelterInfoBot.configuration.InfoBotConfiguration;
import com.skypro.animalShelterInfoBot.model.PetReport;
import com.skypro.animalShelterInfoBot.repositories.PetReportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.junit.Assert.assertEquals;
class TelegramBotTest {
    @Test
    void testGetBotUsername() {
        InfoBotConfiguration config = Mockito.mock(InfoBotConfiguration.class);
        Mockito.when(config.getBotName()).thenReturn("TestBot");

        TelegramBot telegramBot = new TelegramBot(config, Mockito.mock(BotService.class), Mockito.mock(PetReportRepository.class));
        assertEquals("TestBot", telegramBot.getBotUsername());
    }

    @Test
    void testGetBotToken() {
        InfoBotConfiguration config = Mockito.mock(InfoBotConfiguration.class);
        Mockito.when(config.getToken()).thenReturn("1234567890:ABCDEF");

        TelegramBot telegramBot = new TelegramBot(config, Mockito.mock(BotService.class), Mockito.mock(PetReportRepository.class));
        assertEquals("1234567890:ABCDEF", telegramBot.getBotToken());
    }

    @Test
    void testOnUpdateReceivedWithMessageAndText() {
        Update update = new Update();
        Message message = new Message();
        message.setText("Hello");
        update.setMessage(message);

        BotService botService = Mockito.mock(BotService.class);
        PetReportRepository petReportRepository = Mockito.mock(PetReportRepository.class);
        TelegramBot telegramBot = new TelegramBot(Mockito.mock(InfoBotConfiguration.class), botService, petReportRepository);

        telegramBot.onUpdateReceived(update);

        Mockito.verify(botService).inputMsg(update);
    }

    @Test
    void testSendMessageWithText() {
        SendMessage message = new SendMessage();
        message.setText("Test Message");

        TelegramBot telegramBot = new TelegramBot(Mockito.mock(InfoBotConfiguration.class), Mockito.mock(BotService.class), Mockito.mock(PetReportRepository.class));

        telegramBot.sendMessage(message);
    }

    @Test
    void testSavingDatabase() throws Exception {
        PhotoSize photo = new PhotoSize();
        photo.setFileId("123456");
        Long chatId = 123456L;
        String messagePet = "Pet message";
        String userName = "User";

        PetReportRepository petReportRepository = Mockito.mock(PetReportRepository.class);

        TelegramBot telegramBot = new TelegramBot(Mockito.mock(InfoBotConfiguration.class), Mockito.mock(BotService.class), petReportRepository);

        telegramBot.savingDatabase(photo, chatId, messagePet, userName);

        Mockito.verify(petReportRepository).save(Mockito.any(PetReport.class));
    }
}