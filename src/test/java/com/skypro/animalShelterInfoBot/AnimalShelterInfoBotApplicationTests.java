package com.skypro.animalShelterInfoBot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootTest
class AnimalShelterInfoBotApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void mainTest() throws TelegramApiException {
		String[] args = {"arg1", "arg2"};
		AnimalShelterInfoBotApplication.main(args);
	}

}
