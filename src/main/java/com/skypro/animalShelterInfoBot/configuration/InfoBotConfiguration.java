package com.skypro.animalShelterInfoBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации телеграм бота
 * имя и токен берется в application.properties
 */
@Data
@Configuration
public class InfoBotConfiguration {

    @Value(value = "${bot.name}")
    String botName;

    @Value(value = "${bot.token}")
    String token;


}