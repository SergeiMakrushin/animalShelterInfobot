package com.skypro.animalShelterInfoBot.configuration;

import com.skypro.animalShelterInfoBot.bot.InfoBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class InfoBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(InfoBot infoBot) throws TelegramApiException {

        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(infoBot);
        return api;
    }
}