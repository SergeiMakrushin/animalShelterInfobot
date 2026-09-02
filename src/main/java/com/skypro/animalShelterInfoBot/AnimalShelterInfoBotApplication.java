package com.skypro.animalShelterInfoBot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@OpenAPIDefinition
@EnableScheduling
@SpringBootApplication
public class AnimalShelterInfoBotApplication {

	public static void main(String[] args)throws TelegramApiException {
		 SpringApplication.run(AnimalShelterInfoBotApplication.class, args);
	}

}
