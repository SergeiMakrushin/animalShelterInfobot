package com.skypro.animalShelterInfoBot.bot;

import com.skypro.animalShelterInfoBot.informationDirectory.ShelterInformationDirectory;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import com.skypro.animalShelterInfoBot.service.AnimalServiceImpl;
import com.skypro.animalShelterInfoBot.service.UserService;
import com.skypro.animalShelterInfoBot.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BotServiceImplTest {
    @Mock
    UserRepository userRepository;
    UserService userService = new UserServiceImpl(userRepository);
    BotService botService = new BotServiceImpl();

    private final long chatIdExample = 1234567890;
    private BotService.Listener listener;

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
   public void testInputMsg() {

       Update update = new Update();
       Message message = new Message();
       Chat chat = new Chat();
       chat.setId(12345L);
       chat.setFirstName("John");
       chat.setLastName("Doe");
       chat.setUserName("johndoe123");
       message.setChat(chat);
       message.setText("Hello");
       update.setMessage(message);

       SendMessage result = botService.inputMsg(update);

       assertNotNull(result);
   }

    @Test
    public void testProcessingTextAndCallbackQuery_AdministrationMenu() {
        long chatId = 123456;
        String text = "BTN_ADMINISTRATION";
        String name = "John";
        String userName = "john_doe";
        String surname = "Doe";

        SendMessage result = botService.checkingTextForContacts(chatId, text, name, userName, surname);

        assertNotNull(result);
    }
    @Test
    public void testCheckingTextForContacts() {
        BotServiceImpl botService = new BotServiceImpl();
        botService.setUserServiceImpl(mock(UserServiceImpl.class));
        botService.setAnimalServiceImpl(mock(String.valueOf(AnimalServiceImpl.class)));
        botService.setUserRepository(mock(UserRepository.class));
        botService.setListener(mock(BotService.Listener.class));

        long chatId = 123456789;
        String text = "Contact info: +1234567890, email@example.com";
        String name = "John";
        String surname = "Doe";
        String userName = "John Doe";

        SendMessage result = botService.checkingTextForContacts(chatId, text, name, surname, userName);

        assertNotNull(result);
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
    public void infoShelterTimeAndAddress() {
        SendMessage InfoShelterTimeAndAddress = botService.infoShelterTimeAndAddress(chatIdExample);
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

    @Test
    public void testCreateButton() {
        String buttonText = "Test Button";
        botService.setListener(listener);
        String callbackData = botService.createButton(buttonText).getCallbackData();

        assertEquals(buttonText, callbackData);
    }
    @Test
    public void testAdministrationMenu() {
        long chatId = 123456789;

        assertNotNull(botService.administrationMenu(chatId));
    }
    @Test
    void testDogsAndCatMenu() {
        long chatId = 12345;
        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(chatId);
        expectedMessage.setText("Вы вошли в меню поиска друга, давайте вместе подберем.");

        SendMessage actualMessage = botService.dogsAndCatMenu(chatId);

        assertEquals(expectedMessage.getChatId(), actualMessage.getChatId());
        assertEquals(expectedMessage.getText(), actualMessage.getText());
    }

    @Test
    void testInstructionAdoptionMenu() {
        long chatId = 54321;
        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(chatId);
        expectedMessage.setText("Здесь находится вся информация о усыновлении животных. ");

        SendMessage actualMessage = botService.instructionAdoptionMenu(chatId);

        assertEquals(expectedMessage.getChatId(), actualMessage.getChatId());
        assertEquals(expectedMessage.getText(), actualMessage.getText());
    }

}


