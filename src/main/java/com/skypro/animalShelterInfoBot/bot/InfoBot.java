package com.skypro.animalShelterInfoBot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class InfoBot extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(InfoBot.class);

    public InfoBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }
    @Value("${bot.name}")
    private String nameBot;

    /**
     * метод получения и обработки сообщения.
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        logger.info("метод получения и обработки сообщения");

//        String message = update.getMessage().getText();
        Long id = update.getMessage().getChatId();
        String name = update.getMessage().getFrom().getFirstName();
        String message = "Привет, " + name + "! Мы получили твоё сообщение и скоро ответим на него";

        sendText(id, message);

    }

    /**
     * Метод отправляет сообщение пользователю.
     * @param id
     * @param message
     * @return
     */
    public String sendText(Long id, String message) {
        logger.info("метод отправки сообщения пользователю");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("ошибка отправки сообщения");
        }
        return message;
    }

    @Override
    public String getBotUsername() {
        return nameBot;
    }
}

