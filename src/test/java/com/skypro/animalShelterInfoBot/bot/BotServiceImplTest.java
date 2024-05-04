package com.skypro.animalShelterInfoBot.bot;

import com.skypro.animalShelterInfoBot.informationDirectory.ShelterInformationDirectory;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import com.skypro.animalShelterInfoBot.service.UserService;
import com.skypro.animalShelterInfoBot.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BotServiceImplTest {
    @Mock
    UserRepository userRepository;
    UserService userService = new UserServiceImpl(userRepository);
    BotService botService = new BotServiceImpl();

    private final long chatIdExample = 1234567890;

    @Test
    void testSettingSendMessage() {
        long chatId = 123456;
        String text = "Test message";

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setParseMode("HTML");
        expectedMessage.setChatId(chatId);
        expectedMessage.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Информация о приюте");
        row.add("Оставить контакты");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        expectedMessage.setReplyMarkup(keyboardMarkup);

        SendMessage resultMessage = botService.settingSendMessage(chatId, text);

        assertEquals(expectedMessage.getParseMode(), resultMessage.getParseMode());
        assertEquals(expectedMessage.getChatId(), resultMessage.getChatId());
        assertEquals(expectedMessage.getText(), resultMessage.getText());
    }
    @Test
    public void reasonsForRefusal() {
        SendMessage reasonsForRefusal = botService.reasonsForRefusal(chatIdExample);

        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(reasonsForRefusal.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = reasonsForRefusal.getText();
        String expectedText = ShelterInformationDirectory.REASONSFORREFUSAL;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void adviceDogHandlers() {
        SendMessage adviceDogHandlers = botService.adviceDogHandlers(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(adviceDogHandlers.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = adviceDogHandlers.getText();
        String expectedText = ShelterInformationDirectory.DOGHANDLERADVICE;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void getContactDogHandlers() {
        SendMessage getContactDogHandlers = botService.getContactDogHandlers(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(getContactDogHandlers.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = getContactDogHandlers.getText();
        String expectedText = ShelterInformationDirectory.CONTACTDOGHANDLERS;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void homeForLimitedOpportunities() {
        SendMessage homeForLimitedOpportunities = botService.homeForLimitedOpportunities(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(homeForLimitedOpportunities.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = homeForLimitedOpportunities.getText();
        String expectedText = ShelterInformationDirectory.HOMEFORLIMITEDOPPORTUNITIES;

        assertEquals(expectedText, actualText);

    }

    @Test
    public void homeForAdults() {
        SendMessage homeForAdults = botService.homeForAdults(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(homeForAdults.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = homeForAdults.getText();
        String expectedText = ShelterInformationDirectory.HOMEFORADULTS;

        assertEquals(expectedText, actualText);

    }

    @Test
    public void homeForChild() {
        SendMessage homeForChild = botService.homeForChild(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(homeForChild.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = homeForChild.getText();
        String expectedText = ShelterInformationDirectory.HOMEFORCHILDPETS;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void recommendationTransportAnimal() {
        SendMessage recommendationTransportAnimal = botService.recommendationTransportAnimal(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(recommendationTransportAnimal.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = recommendationTransportAnimal.getText();
        String expectedText = ShelterInformationDirectory.RECOMMENDATIONTRANSPORTANIMAL;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void adoptionDocuments() {
        SendMessage adoptionDocuments = botService.adoptionDocuments(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(adoptionDocuments.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = adoptionDocuments.getText();
        String expectedText = ShelterInformationDirectory.LISTSDOCSDECOR;

        assertEquals(expectedText, actualText);
    }


    @Test
    public void meetingAnimals() {
        SendMessage meetingAnimals = botService.meetingAnimals(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(meetingAnimals.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = meetingAnimals.getText();
        String expectedText = ShelterInformationDirectory.MEETINGANIMALS;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void shelterTB() {
        SendMessage shelterTB = botService.shelterTB(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(shelterTB.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = shelterTB.getText();
        String expectedText = ShelterInformationDirectory.TBSHELTER;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void registerPass() {
        SendMessage registerPass = botService.registerPass(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(registerPass.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = registerPass.getText();
        String expectedText = ShelterInformationDirectory.REGISTERPASS;

        assertEquals(expectedText, actualText);
    }

    @Test
    public void InfoShelterTimeAndAddress() {
        SendMessage InfoShelterTimeAndAddress = botService.InfoShelterTimeAndAddress(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(InfoShelterTimeAndAddress.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = InfoShelterTimeAndAddress.getText();
        String expectedText = ShelterInformationDirectory.SHELTERADRESS + ShelterInformationDirectory.WORKTIME;

        assertEquals(expectedText, actualText);
    }


    @Test
    public void infoShelter() {
        SendMessage infoShelter = botService.infoShelter(chatIdExample);
        long actualChatId = 0;
        try {
            actualChatId = Long.parseLong(infoShelter.getChatId());
        } catch (NumberFormatException nfe) {
            System.out.println("Ошибка при преобразовании строки в число");
        }

        assertEquals(chatIdExample, actualChatId);

        String actualText = infoShelter.getText();
        String expectedText = ShelterInformationDirectory.SHELTERINFO;

        assertEquals(expectedText, actualText);
    }
}


