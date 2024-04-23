package com.skypro.animalShelterInfoBot.service.bot;

import com.skypro.animalShelterInfoBot.informationDirectory.ShelterInformationDirectory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Slf4j
@Service
public class BotService {


    @Value("${bot.token}")
    private String token;

    /**
     * Создаем List с названием всех кнопок меню
     */

    public final List<String> NAME_BUTTONS = new ArrayList<>(List.of(
            //Стартовое меню
            // индекс 0 - 1 - 2
            "Администрация", "Отдел собак", "Отдел кошек",

            //Меню администрация
            // индекс 3 - 4
            "Информация о приюте", "Часы работы, адрес",
            // индекс 5 - 6
            "Прислать отчет", "Как получить питомца",
            // индекс 7 - 8
            "Получить пропуск", "ТБ на территории",
            // индекс 9 - 10
            "Оставить контакты", "позвать волонтера",
            // индекс 11
            "На главное меню",

            //меню Отдел собак (Отдел кошек)
            // индекс 12 - 13
            "Показать всех", "Найти по кличке",
            // индекс 14 - 15
            "Найти по возрасту", "Найти по окрасу",
            // индекс 16
            "Найти по породе",

            //меню взятия животного
            // индекс 17 - 18
            "Правила знакомства с животным", "Список документов для оформления",
            // индекс 19 - 20
            "Рекомендации транспортировки", "обустройство дома для детеныша",
            // индекс 21 - 22
            "обустройство дома для взрослого питомца", "обустройство дома для питомца с огр возможностями",
            // индекс 23 - 24
            "Получить контакты кинологов", "Советы кинолога",
            // индекс 25
            "Причины отказа"));

    /**
     * Настройка сообщений
     *
     * @param chatId
     * @param text
     * @return message
     */
    public SendMessage settingSendMessage(long chatId, String text) {
        SendMessage msg = new SendMessage();
        msg.setParseMode("HTML");
        msg.setChatId(chatId);
        msg.setText(text);
        return msg;
    }

    /**
     * Проверка что пришло от пользователя
     * нажатие клавиши или текст
     *
     * @param update
     * @return
     */
    SendMessage inputMsg(Update update) {
        SendMessage textToSend = new SendMessage();

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String msgText = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            String name = callbackQuery.getFrom().getFirstName();
            textToSend = processingTextAndCallbackQuery(chatId, msgText, name);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            String msgText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
            textToSend = processingTextAndCallbackQuery(chatId, msgText, name);
        }
        return textToSend;
    }

    /**
     * Обработка входящих сообщений
     * Обработка нажатий кнопок меню
     *
     * @param chatId
     * @param text
     * @param name
     * @return
     */
    SendMessage processingTextAndCallbackQuery(long chatId, String text, String name) {
        SendMessage textToSend;
        //проходим по индексам кнопок
        int indexButton = -1;
        for (int i = 0; i < NAME_BUTTONS.size(); i++) {
            if (NAME_BUTTONS.get(i).equalsIgnoreCase(text)) {
                indexButton = i;
                break;
            }
        }
        //Команды кнопок
        switch (indexButton) {
            case 0:
                log.info("Нажата клавиша");
                textToSend = administrationMenu(chatId);
                break;
            case 1, 2:                                    //Настроить логику выбора
                textToSend = dogsAndCatMenu(chatId);      //Кошки и собаки
                break;
            ////////
            case 3:
                textToSend = infoShelter(chatId);
                break;
            case 4:
                textToSend = InfoShelterTimeAndAddress(chatId);
                break;
            //////
            case 5:
                textToSend = sendReport(chatId);
                break;
            case 6:
                textToSend = instructionAdoptionMenu(chatId);
                break;
            case 7:
                textToSend = registerPass(chatId);
                break;
            case 8:
                textToSend = shelterTB(chatId);
                break;
            case 9:
                textToSend = leaveContact(chatId);
                break;
            case 10:
                textToSend = getContactVolunteer(chatId);
                break;
            case 11:
                textToSend = sendStartMenu(chatId, name);  //Написать логику, если пользователь уже есть в БД - не приветствовать
                break;
            case 12:
                textToSend = getAllDogAndCat(chatId);
                break;
            case 13:
                textToSend = getNameDogAndCat(chatId);
                break;
            //////
            case 14:
                textToSend = getAgeDogAndCat(chatId);
                break;
            case 15:
                textToSend = getColorDogAndCat(chatId);
                break;
            ///////
            case 16:
                textToSend = getBreedDogAndColor(chatId);
                break;
            case 17:
                textToSend = meetingAnimals(chatId);
                break;
            case 18:
                textToSend = listDocsDecor(chatId);
                break;
            case 19:
                textToSend = recommendationTransportAnimal(chatId);
                break;
            case 20:
                textToSend = homeForChild(chatId);
                break;
            case 21:
                textToSend = homeForAdults(chatId);
                break;
            case 22:
                textToSend = homeForLimitedOpportunities(chatId);
                break;
            case 23:
                textToSend = getContactDogHandlers(chatId);
                break;
            case 24:
                textToSend = adviceDogHandlers(chatId);
                break;
            case 25:
                textToSend = reasonsForRefusal(chatId);
                break;
            default:
                textToSend = settingSendMessage(chatId, "Выберите интересующую вас кнопку меню \uD83D\uDC47 \n" +
                        "Или воспользуйтесь кнопкой вызова волонтера, он вам поможет \uD83D\uDE09");
                break;
        }
        if (text.equals("/start")) {
            textToSend = sendStartMenu(chatId, name);
        } else if (text.equals("/info_shelter")) {
            textToSend = infoShelter(chatId);
        } else if (text.equals("/info_take_animal")) {
            textToSend = instructionAdoptionMenu(chatId);
        } else if (text.equals("/send_report")) {
            textToSend = sendReport(chatId);
        } else if (text.equals("/leave_contact")) {
            textToSend = leaveContact(chatId);
        } else if (text.equals("/help")) {
            textToSend = getContactVolunteer(chatId);
        } else if (text.equals("/get_pass")) {
            textToSend = registerPass(chatId);
        } else if (text.equals("/tb_recommendations")) {
            textToSend = shelterTB(chatId);
        } else if (text.equals("/location")) {
            textToSend = InfoShelterTimeAndAddress(chatId);
        } else if (text.equals("/dogs")) {
            textToSend = dogsAndCatMenu(chatId);
        } else if (text.equals("/cats")) {
            textToSend = dogsAndCatMenu(chatId);
        }
        return textToSend;
    }

    private SendMessage reasonsForRefusal(long chatId) {
        SendMessage reasonsForRefusal = new SendMessage();
        reasonsForRefusal.setChatId(chatId);
        reasonsForRefusal.setText("Пожалуйста, свяжитесь с нами для получения дополнительной информации о причинах отказа.");
        return reasonsForRefusal;
    }

    private SendMessage adviceDogHandlers(long chatId) {
        SendMessage adviceDogHandlers = new SendMessage();
        adviceDogHandlers.setChatId(chatId);
        adviceDogHandlers.setText("Пожалуйста, обратитесь к нашим собаководам за консультацией.");
        return adviceDogHandlers;
    }

    private SendMessage getContactDogHandlers(long chatId) {
        SendMessage getContactDogHandlers = new SendMessage();
        getContactDogHandlers.setChatId(chatId);
        getContactDogHandlers.setText("Контактную информацию наших кинологов можно предоставить по запросу.");
        return getContactDogHandlers;
    }

    private SendMessage homeForLimitedOpportunities(long chatId) {
        SendMessage homeForLimitedOpportunities = new SendMessage();
        homeForLimitedOpportunities.setChatId(chatId);
        homeForLimitedOpportunities.setText("Наша программа по уходу за домашними животными с ограниченными" +
                " возможностями предлагает любящую среду для животных с особыми потребностями.");
        return homeForLimitedOpportunities;
    }

    private SendMessage homeForAdults(long chatId) {
        SendMessage homeForAdults = new SendMessage();
        homeForAdults.setChatId(chatId);
        homeForAdults.setText("У нас есть специальная программа для взрослых животных, которые ищут постоянный дом.");
        return homeForAdults;
    }

    private SendMessage homeForChild(long chatId) {
        SendMessage homeForChild = new SendMessage();
        homeForChild.setChatId(chatId);
        homeForChild.setText("Если вы заинтересованы в усыновлении пушистого друга для вашего ребенка, пожалуйста, свяжитесь с нами.");
        return homeForChild;
    }

    private SendMessage recommendationTransportAnimal(long chatId) {
        SendMessage transportAnimal = new SendMessage();
        transportAnimal.setChatId(chatId);
        transportAnimal.setText("Мы можем порекомендовать транспортные услуги для вашего нового питомца.");
        return transportAnimal;
    }

    private SendMessage listDocsDecor(long chatId) {
        SendMessage listDocsDecor = new SendMessage();
        listDocsDecor.setChatId(chatId);
        listDocsDecor.setText("Документы, необходимые для усыновления, будут предоставлены по запросу.");
        return listDocsDecor;
    }

    private SendMessage meetingAnimals(long chatId) {
        SendMessage shelterInfo = new SendMessage();
        shelterInfo.setChatId(chatId);
        shelterInfo.setText("Вы можете назначить встречу с нашими животными, связавшись с нами.");
        return shelterInfo;
    }

    private SendMessage getBreedDogAndColor(long chatId) {
        SendMessage getBreedDogAndColor = new SendMessage();
        getBreedDogAndColor.setChatId(chatId);
        getBreedDogAndColor.setText("Пожалуйста, укажите породу и предпочтительный окрас собаки, которую вы ищете.");
        return getBreedDogAndColor;
    }

    ///////////////
    private SendMessage getColorDogAndCat(long chatId) {
        SendMessage getColorDogAndCat = new SendMessage();
        getColorDogAndCat.setChatId(chatId);
        getColorDogAndCat.setText("Дайте нам знать предпочтения по цвету для собаки или кота, которого вы хотели бы усыновить.");
        return getColorDogAndCat;
    }

    private SendMessage getAgeDogAndCat(long chatId) {
        SendMessage getAgeDogAndCat = new SendMessage();
        getAgeDogAndCat.setChatId(chatId);
        getAgeDogAndCat.setText("Какой возрастной диапазон вы ищете у собаки или кошки?");
        return getAgeDogAndCat;
    }

    private SendMessage getNameDogAndCat(long chatId) {
        SendMessage getNameDogAndCat = new SendMessage();
        getNameDogAndCat.setChatId(chatId);
        getNameDogAndCat.setText("Пожалуйста, укажите имя, которое вы хотите дать собаке или кошке, которую вы усыновляете.");
        return getNameDogAndCat;
    }

    private SendMessage getAllDogAndCat(long chatId) {
        SendMessage getAllDogAndCat = new SendMessage();
        getAllDogAndCat.setChatId(chatId);
        getAllDogAndCat.setText("У нас есть разнообразие собак и кошек, доступных для усыновления. " +
                "Свяжитесь с нами для получения более подробной информации.");
        return getAllDogAndCat;
    }

    //////////////////////
    private SendMessage getContactVolunteer(long chatId) {
        SendMessage getContactVolunteer = new SendMessage();
        getContactVolunteer.setChatId(chatId);
        getContactVolunteer.setText("Наши добровольцы готовы помочь вам. Свяжитесь с нами для возможностей добровольчества.");
        return getContactVolunteer;
    }

    private SendMessage leaveContact(long chatId) {
        SendMessage leaveContact = new SendMessage();
        leaveContact.setChatId(chatId);
        leaveContact.setText("Пожалуйста, оставьте ваш контактную информацию, чтобы мы могли связаться с вами.");
        return leaveContact;
    }

    private SendMessage shelterTB(long chatId) {
        SendMessage shelterTB = new SendMessage();
        shelterTB.setChatId(chatId);
        shelterTB.setText("Посетите наш приют, чтобы увидеть наших пушистых друзей лично.");
        return shelterTB;
    }

    private SendMessage registerPass(long chatId) {
        SendMessage registerPass = new SendMessage();
        registerPass.setChatId(chatId);
        registerPass.setText("Вы можете зарегистрироваться, чтобы получить пропуск для посещения нашего приюта.");
        return registerPass;
    }

    private SendMessage sendReport(long chatId) {
        SendMessage sendReport = new SendMessage();
        sendReport.setChatId(chatId);
        sendReport.setText("Если у вас есть какие-либо вопросы или нужно сообщить о проблеме, пожалуйста, свяжитесь с нами.");
        return sendReport;
    }

    ////////
    private SendMessage InfoShelterTimeAndAddress(long chatId) {
        SendMessage timeAndAddress = new SendMessage();
        timeAndAddress.setChatId(chatId);
        timeAndAddress.setText(ShelterInformationDirectory.SHELTERADRESS + ShelterInformationDirectory.WORKTIME);
        return timeAndAddress;
    }

    private SendMessage infoShelter(long chatId) {
        SendMessage shelterInfo = new SendMessage();
        shelterInfo.setChatId(chatId);
        shelterInfo.setText(ShelterInformationDirectory.SHELTERINFO);
        return shelterInfo;
    }
///////////

    /**
     * Стартовое меню
     *
     * @param chatId
     * @param name
     * @return message
     */
    public SendMessage sendStartMenu(long chatId, String name) {
        log.info("Идет инициализация стартового меню... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Привет " + name + "! Добро пожаловать в приют пушистых друзей! \n" + "Я помогу вам найти верного друга!\n\n" + "Выберите интересующую вас кнопку меню \uD83D\uDC47");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton(); //Создаем кнопку

        inlineKeyboardButton1.setText(NAME_BUTTONS.get(0)); //Текст самой кнопки
        inlineKeyboardButton1.setCallbackData(NAME_BUTTONS.get(0)); //Отклик на нажатие кнопки
        inlineKeyboardButton2.setText(NAME_BUTTONS.get(1)); //Текст самой кнопки
        inlineKeyboardButton2.setCallbackData(NAME_BUTTONS.get(1)); //Отклик на нажатие кнопки
        inlineKeyboardButton3.setText(NAME_BUTTONS.get(2)); //Текст самой кнопки
        inlineKeyboardButton3.setCallbackData(NAME_BUTTONS.get(2)); //Отклик на нажатие кнопки

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём ряд
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        msg.setReplyMarkup(inlineKeyboardMarkup);

        return msg;
    }

    /**
     * Создаем меню администрации
     *
     * @param chatId
     * @return message
     */
    SendMessage administrationMenu(long chatId) {
        log.info("Идет инициализация меню администрации... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Вы вошли в администрацию, что вас интересует? \n");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton8 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton9 = new InlineKeyboardButton(); //Создаем кнопку

        inlineKeyboardButton1.setText(NAME_BUTTONS.get(3)); //Текст самой кнопки
        inlineKeyboardButton1.setCallbackData(NAME_BUTTONS.get(3)); //Отклик на нажатие кнопки
        inlineKeyboardButton2.setText(NAME_BUTTONS.get(4)); //Текст самой кнопки
        inlineKeyboardButton2.setCallbackData(NAME_BUTTONS.get(4)); //Отклик на нажатие кнопки
        inlineKeyboardButton3.setText(NAME_BUTTONS.get(5)); //Текст самой кнопки
        inlineKeyboardButton3.setCallbackData(NAME_BUTTONS.get(5)); //Отклик на нажатие кнопки
        inlineKeyboardButton4.setText(NAME_BUTTONS.get(6)); //Текст самой кнопки
        inlineKeyboardButton4.setCallbackData(NAME_BUTTONS.get(6)); //Отклик на нажатие кнопки
        inlineKeyboardButton5.setText(NAME_BUTTONS.get(7)); //Текст самой кнопки
        inlineKeyboardButton5.setCallbackData(NAME_BUTTONS.get(7)); //Отклик на нажатие кнопки
        inlineKeyboardButton6.setText(NAME_BUTTONS.get(8)); //Текст самой кнопки
        inlineKeyboardButton6.setCallbackData(NAME_BUTTONS.get(8)); //Отклик на нажатие кнопки
        inlineKeyboardButton7.setText(NAME_BUTTONS.get(9)); //Текст самой кнопки
        inlineKeyboardButton7.setCallbackData(NAME_BUTTONS.get(9)); //Отклик на нажатие кнопки
        inlineKeyboardButton8.setText(NAME_BUTTONS.get(10)); //Текст самой кнопки
        inlineKeyboardButton8.setCallbackData(NAME_BUTTONS.get(10)); //Отклик на нажатие кнопки
        inlineKeyboardButton9.setText(NAME_BUTTONS.get(11)); //Текст самой кнопки
        inlineKeyboardButton9.setCallbackData(NAME_BUTTONS.get(11)); //Отклик на нажатие кнопки

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>(); //Создаем ряд кнопок

        keyboardButtonsRow1.add(inlineKeyboardButton1); //Добавляем кнопки в ряд
        keyboardButtonsRow1.add(inlineKeyboardButton2); //Добавляем кнопки в ряд
        keyboardButtonsRow2.add(inlineKeyboardButton3); //Добавляем кнопки в ряд
        keyboardButtonsRow2.add(inlineKeyboardButton4); //Добавляем кнопки в ряд
        keyboardButtonsRow3.add(inlineKeyboardButton5); //Добавляем кнопки в ряд
        keyboardButtonsRow3.add(inlineKeyboardButton6); //Добавляем кнопки в ряд
        keyboardButtonsRow4.add(inlineKeyboardButton7); //Добавляем кнопки в ряд
        keyboardButtonsRow4.add(inlineKeyboardButton8); //Добавляем кнопки в ряд
        keyboardButtonsRow5.add(inlineKeyboardButton9); //Добавляем кнопки в ряд

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём лист листов и закидываем списки рядов
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);

        inlineKeyboardMarkup.setKeyboard(rowList); //Вносим настройки в клавиатуру

        msg.setReplyMarkup(inlineKeyboardMarkup); //Изменяем клавиатуру

        return msg; //Отправляем сообщение
    }

    public SendMessage dogsAndCatMenu(long chatId) {
        log.info("Идет инициализация меню животных... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Вы вошли в меню поиска друга, давайте вместе подберем.");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton(); //Создаем кнопку

        inlineKeyboardButton1.setText(NAME_BUTTONS.get(12)); //Текст самой кнопки
        inlineKeyboardButton1.setCallbackData(NAME_BUTTONS.get(12)); //Отклик на нажатие кнопки
        inlineKeyboardButton2.setText(NAME_BUTTONS.get(13)); //Текст самой кнопки
        inlineKeyboardButton2.setCallbackData(NAME_BUTTONS.get(13)); //Отклик на нажатие кнопки
        inlineKeyboardButton3.setText(NAME_BUTTONS.get(14)); //Текст самой кнопки
        inlineKeyboardButton3.setCallbackData(NAME_BUTTONS.get(14)); //Отклик на нажатие кнопки
        inlineKeyboardButton4.setText(NAME_BUTTONS.get(15)); //Текст самой кнопки
        inlineKeyboardButton4.setCallbackData(NAME_BUTTONS.get(15)); //Отклик на нажатие кнопки
        inlineKeyboardButton5.setText(NAME_BUTTONS.get(16)); //Текст самой кнопки
        inlineKeyboardButton5.setCallbackData(NAME_BUTTONS.get(16)); //Отклик на нажатие кнопки

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>(); //Создаем ряд кнопок

        keyboardButtonsRow1.add(inlineKeyboardButton1); //Добавляем кнопки в ряд
        keyboardButtonsRow2.add(inlineKeyboardButton2); //Добавляем кнопки в ряд
        keyboardButtonsRow2.add(inlineKeyboardButton3); //Добавляем кнопки в ряд
        keyboardButtonsRow3.add(inlineKeyboardButton4); //Добавляем кнопки в ряд
        keyboardButtonsRow3.add(inlineKeyboardButton5); //Добавляем кнопки в ряд

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём лист листов и закидываем списки рядов
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList); //Вносим настройки в клавиатуру

        msg.setReplyMarkup(inlineKeyboardMarkup); //Изменяем клавиатуру

        return msg; //Отправляем сообщение
    }

    public SendMessage instructionAdoptionMenu(long chatId) {
        log.info("Идет инициализация меню как взять животное ... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Здесь находится вся информация о усыновлении животных. ");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton8 = new InlineKeyboardButton(); //Создаем кнопку
        InlineKeyboardButton inlineKeyboardButton9 = new InlineKeyboardButton(); //Создаем кнопку

        inlineKeyboardButton1.setText(NAME_BUTTONS.get(17)); //Текст самой кнопки
        inlineKeyboardButton1.setCallbackData(NAME_BUTTONS.get(17)); //Отклик на нажатие кнопки
        inlineKeyboardButton2.setText(NAME_BUTTONS.get(18)); //Текст самой кнопки
        inlineKeyboardButton2.setCallbackData(NAME_BUTTONS.get(18)); //Отклик на нажатие кнопки
        inlineKeyboardButton3.setText(NAME_BUTTONS.get(19)); //Текст самой кнопки
        inlineKeyboardButton3.setCallbackData(NAME_BUTTONS.get(19)); //Отклик на нажатие кнопки
        inlineKeyboardButton4.setText(NAME_BUTTONS.get(20)); //Текст самой кнопки
        inlineKeyboardButton4.setCallbackData(NAME_BUTTONS.get(20)); //Отклик на нажатие кнопки
        inlineKeyboardButton5.setText(NAME_BUTTONS.get(21)); //Текст самой кнопки
        inlineKeyboardButton5.setCallbackData(NAME_BUTTONS.get(21)); //Отклик на нажатие кнопки
        inlineKeyboardButton6.setText(NAME_BUTTONS.get(22)); //Текст самой кнопки
        inlineKeyboardButton6.setCallbackData(NAME_BUTTONS.get(22)); //Отклик на нажатие кнопки
        inlineKeyboardButton7.setText(NAME_BUTTONS.get(23)); //Текст самой кнопки
        inlineKeyboardButton7.setCallbackData(NAME_BUTTONS.get(23)); //Отклик на нажатие кнопки
        inlineKeyboardButton8.setText(NAME_BUTTONS.get(24)); //Текст самой кнопки
        inlineKeyboardButton8.setCallbackData(NAME_BUTTONS.get(24)); //Отклик на нажатие кнопки
        inlineKeyboardButton9.setText(NAME_BUTTONS.get(25)); //Текст самой кнопки
        inlineKeyboardButton9.setCallbackData(NAME_BUTTONS.get(25)); //Отклик на нажатие кнопки

        List<InlineKeyboardButton> buttonsRow1 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow2 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow3 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow4 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow5 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow6 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow7 = new ArrayList<>(); //Создаем ряд кнопок
        List<InlineKeyboardButton> buttonsRow8 = new ArrayList<>(); //Создаем ряд кнопок

        buttonsRow1.add(inlineKeyboardButton1); //Добавляем кнопки в ряд
        buttonsRow2.add(inlineKeyboardButton2); //Добавляем кнопки в ряд
        buttonsRow3.add(inlineKeyboardButton3); //Добавляем кнопки в ряд
        buttonsRow4.add(inlineKeyboardButton4); //Добавляем кнопки в ряд
        buttonsRow5.add(inlineKeyboardButton5); //Добавляем кнопки в ряд
        buttonsRow6.add(inlineKeyboardButton6); //Добавляем кнопки в ряд
        buttonsRow7.add(inlineKeyboardButton7); //Добавляем кнопки в ряд
        buttonsRow8.add(inlineKeyboardButton8); //Добавляем кнопки в ряд
        buttonsRow8.add(inlineKeyboardButton9); //Добавляем кнопки в ряд

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём лист листов и закидываем списки рядов
        rowList.add(buttonsRow1);
        rowList.add(buttonsRow2);
        rowList.add(buttonsRow3);
        rowList.add(buttonsRow4);
        rowList.add(buttonsRow5);
        rowList.add(buttonsRow6);
        rowList.add(buttonsRow7);
        rowList.add(buttonsRow8);

        inlineKeyboardMarkup.setKeyboard(rowList); //Вносим настройки в клавиатуру

        msg.setReplyMarkup(inlineKeyboardMarkup); //Изменяем клавиатуру

        return msg; //Отправляем сообщение
    }
}

