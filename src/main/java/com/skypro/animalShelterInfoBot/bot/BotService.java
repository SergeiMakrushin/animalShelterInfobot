package com.skypro.animalShelterInfoBot.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public interface BotService {

    SendMessage settingSendMessage(long chatId, String text);

    void registerUserAndWelcome(long chatId, String name, String surname);

    SendMessage checkingTextForContacts(long chatId, String text, String name, String surname, String userName);

    SendMessage checkingTextForColor(long chatId, String text);

    SendMessage inputMsg(Update update);

    SendMessage reasonsForRefusal(long chatId);

    SendMessage adviceDogHandlers(long chatId);

    SendMessage getContactDogHandlers(long chatId);

    SendMessage homeForLimitedOpportunities(long chatId);

    SendMessage homeForAdults(long chatId);

    SendMessage homeForChild(long chatId);

    SendMessage recommendationTransportAnimal(long chatId);

    SendMessage adoptionDocuments(long chatId);

    SendMessage meetingAnimals(long chatId);

    SendMessage getBreedDogAndCat(long chatId);

    SendMessage getColorDogAndCat(long chatId);

    SendMessage getAgeDogAndCat(long chatId);

    SendMessage getNameDogAndCat(long chatId);

    SendMessage getAllDogAndCat(long chatId);

    SendMessage getContactVolunteer(long chatId, String userName);

    SendMessage leaveContact(long chatId, String text);

    SendMessage shelterTB(long chatId);

    SendMessage registerPass(long chatId);


    SendMessage sendReport(PhotoSize photo, long chatId, String messagePet,  String userName);

    SendMessage infoShelterTimeAndAddress(long chatId);

    SendMessage infoShelter(long chatId);

    SendMessage sendStartMenu(long chatId, String name, String surname);

    SendMessage administrationMenu(long chatId);

    SendMessage dogsAndCatMenu(long chatId);

    SendMessage instructionAdoptionMenu(long chatId);

    InlineKeyboardButton createButton(String text);

    void setListener(Listener listener);

    interface Listener {
        void sendMessage(SendMessage message);

        void sendMessage(SendPhoto sendPhoto);

        void savingDatabase(PhotoSize photo, Long chatId, String messagePet,  String userName);

    }
}
