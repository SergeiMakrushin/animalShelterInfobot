package com.skypro.animalShelterInfoBot.bot;

import com.skypro.animalShelterInfoBot.informationDirectory.ShelterInformationDirectory;
import com.skypro.animalShelterInfoBot.model.User;
import com.skypro.animalShelterInfoBot.service.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@RequiredArgsConstructor
@Slf4j
@Service
public class BotServiceImpl implements BotService {

    private static final String BTN_ADMINISTRATION = "Администрация";
    private static final String BTN_DOGS = "Отдел собак";
    private static final String BTN_CATS = "Отдел кошек";
    private static final String BTN_INFO_SHELTER = "Информация о приюте";
    private static final String BTN_LOCATION = "Часы работы, адрес";
    private static final String BTN_SEND_REPORT = "Прислать отчет";
    private static final String BTN_INFO_TAKE_ANIMAL = "Как получить питомца";
    private static final String BTN_GET_PASS = "Получить пропуск";
    private static final String BTN_TB_RECOMMENDATION = "ТБ на территории";
    private static final String BTN_LEAVE_CONTACTS = "Оставить контакты";
    private static final String BTN_HELP = "Позвать волонтера";
    private static final String BTN_MAIN_MENU = "На главное меню";
    private static final String BTN_SHOW_ALL = "Показать всех";
    private static final String BTN_FIND_BY_NICK = "Найти по кличке";
    private static final String BTN_FIND_BY_AGE = "Найти по возрасту";
    private static final String BTN_FIND_BY_COLOR = "Найти по окрасу";
    private static final String BTN_FIND_BY_BREED = "Найти по породе";
    private static final String BTN_RULES_TO_MEETING = "Правила знакомства";
    private static final String BTN_DOCUMENTS_LISTS = "Список документов";
    private static final String BTN_TRANSPORT_RECOMMENDATION = "Как перевозить";
    private static final String BTN_HOME_FOR_CUB = "Дом для малыша";
    private static final String BTN_HOME_FOR_ADULT = "Дом для взрослого";
    private static final String BTN_HOME_FOR_DISABLE = "Дом для инвалида";
    private static final String BTN_HANDLERS_CONTACT = "Контакты кинологов";
    private static final String BTN_HANDLERS_TIPS = "Советы кинолога";
    private static final String BTN_REFUSE_REASONS = "Причины отказа";

    static final String CMD_START = "/start";
    static final String CMD_INFO_SHELTER = "/info_shelter";
    static final String CMD_INFO_TAKE_ANIMAL = "/info_take_animal";
    static final String CMD_SEND_REPORT = "/send_report";
    static final String CMD_LEAVE_CONTACT = "/leave_contact";
    static final String CMD_HELP = "/help";
    static final String CMD_GET_PASS = "/get_pass";
    static final String CMD_TB_RECOMMENDATIONS = "/tb_recommendations";
    static final String CMD_LOCATION = "/location";
    static final String CMD_DOGS = "/dogs";
    static final String CMD_CATS = "/cats";

    @Autowired
    UserServiceImpl userServiceImpl;

    /**
     * Создаем постоянную клавиатуру
     *
     * @param chatId Id чата
     * @param text   Текст сообщения
     * @return message
     */
    public SendMessage settingSendMessage(long chatId, String text) {
        log.info("метод отправки сообщения пользователю");

        SendMessage msg = new SendMessage();
        msg.setParseMode("HTML");
        msg.setChatId(chatId);
        msg.setText(text);

        //Создаем постоянную клавиатуру
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        //создаем лист рядов
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        //добавляем ряд и кнопки
        KeyboardRow row = new KeyboardRow();
        row.add("Информация о приюте");
        row.add("Оставить контакты");
        keyboardRows.add(row);

        //добавляем еще ряд и кнопки
        row = new KeyboardRow();
        row.add("Позвать волонтера");
        row.add("На главное меню");
        keyboardRows.add(row);

        //перегружаем в лист рядов, меняем клавиатуру
        keyboardMarkup.setKeyboard(keyboardRows);
        msg.setReplyMarkup(keyboardMarkup);

        //Отправляем сообщение
        return (msg);
    }

    /**
     * Проверка, что пришло от пользователя
     * нажатие клавиши или текст
     *
     * @param update данные от пользователя
     * @return текст для отправки
     */
    public SendMessage inputMsg(Update update) {
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
     * @param chatId Id чата
     * @param text   текст, который пришел от пользователя
     * @param name   имя пользователя
     * @return сообщение для пользователя
     */
    public SendMessage processingTextAndCallbackQuery(long chatId, String text, String name) {
        log.info("Нажата клавиша \"" + text + "\"");

        return switch (text) {
            case BTN_ADMINISTRATION -> administrationMenu(chatId);
            case BTN_DOGS, BTN_CATS, CMD_DOGS, CMD_CATS ->
                    dogsAndCatMenu(chatId);            //Настроить логику выбора (Кошки и собаки)
            case BTN_INFO_SHELTER, CMD_INFO_SHELTER -> infoShelter(chatId);
            case BTN_LOCATION, CMD_LOCATION -> InfoShelterTimeAndAddress(chatId);
            case BTN_SEND_REPORT, CMD_SEND_REPORT -> sendReport(chatId);
            case BTN_INFO_TAKE_ANIMAL, CMD_INFO_TAKE_ANIMAL -> instructionAdoptionMenu(chatId);
            case BTN_GET_PASS, CMD_GET_PASS -> registerPass(chatId);
            case BTN_TB_RECOMMENDATION, CMD_TB_RECOMMENDATIONS -> shelterTB(chatId);
            case BTN_LEAVE_CONTACTS, CMD_LEAVE_CONTACT -> leaveContact(chatId);
            case BTN_HELP, CMD_HELP -> getContactVolunteer(chatId);
            case BTN_MAIN_MENU ->
                    sendStartMenu(chatId, name);         //Написать логику, если пользователь уже есть в БД - не приветствовать
            case BTN_SHOW_ALL -> getAllDogAndCat(chatId);
            case BTN_FIND_BY_NICK -> getNameDogAndCat(chatId);
            case BTN_FIND_BY_AGE -> getAgeDogAndCat(chatId);
            case BTN_FIND_BY_COLOR -> getColorDogAndCat(chatId);
            case BTN_FIND_BY_BREED -> getBreedDogAndColor(chatId);
            case BTN_RULES_TO_MEETING -> meetingAnimals(chatId);
            case BTN_DOCUMENTS_LISTS -> adoptionDocuments(chatId);
            case BTN_TRANSPORT_RECOMMENDATION -> recommendationTransportAnimal(chatId);
            case BTN_HOME_FOR_CUB -> homeForChild(chatId);
            case BTN_HOME_FOR_ADULT -> homeForAdults(chatId);
            case BTN_HOME_FOR_DISABLE -> homeForLimitedOpportunities(chatId);
            case BTN_HANDLERS_CONTACT -> getContactDogHandlers(chatId);
            case BTN_HANDLERS_TIPS -> adviceDogHandlers(chatId);
            case BTN_REFUSE_REASONS -> reasonsForRefusal(chatId);
            case CMD_START -> sendStartMenu(chatId, name);
            default -> settingSendMessage(chatId, "Выберите интересующую вас кнопку меню \uD83D\uDC47 \n" +
                    "Или воспользуйтесь кнопкой вызова волонтера, он вам поможет \uD83D\uDE09");
        };
    }

    public SendMessage reasonsForRefusal(long chatId) {
        SendMessage reasonsForRefusal = new SendMessage();
        reasonsForRefusal.setChatId(chatId);
        reasonsForRefusal.setText(ShelterInformationDirectory.REASONSFORREFUSAL);
        return reasonsForRefusal;
    }

    public SendMessage adviceDogHandlers(long chatId) {
        SendMessage adviceDogHandlers = new SendMessage();
        adviceDogHandlers.setChatId(chatId);
        adviceDogHandlers.setText(ShelterInformationDirectory.DOGHANDLERADVICE);
        return adviceDogHandlers;
    }

    public SendMessage getContactDogHandlers(long chatId) {
        SendMessage getContactDogHandlers = new SendMessage();
        getContactDogHandlers.setChatId(chatId);
        getContactDogHandlers.setText(ShelterInformationDirectory.CONTACTDOGHANDLERS);
        return getContactDogHandlers;
    }

    public SendMessage homeForLimitedOpportunities(long chatId) {
        SendMessage homeForLimitedOpportunities = new SendMessage();
        homeForLimitedOpportunities.setChatId(chatId);
        homeForLimitedOpportunities.setText(ShelterInformationDirectory.HOMEFORLIMITEDOPPORTUNITIES);
        return homeForLimitedOpportunities;
    }

    public SendMessage homeForAdults(long chatId) {
        SendMessage homeForAdults = new SendMessage();
        homeForAdults.setChatId(chatId);
        homeForAdults.setText(ShelterInformationDirectory.HOMEFORADULTS);
        return homeForAdults;
    }

    public SendMessage homeForChild(long chatId) {
        SendMessage homeForChild = new SendMessage();
        homeForChild.setChatId(chatId);
        homeForChild.setText(ShelterInformationDirectory.HOMEFORCHILDPETS);
        return homeForChild;
    }

    public SendMessage recommendationTransportAnimal(long chatId) {
        SendMessage transportAnimal = new SendMessage();
        transportAnimal.setChatId(chatId);
        transportAnimal.setText(ShelterInformationDirectory.RECOMMENDATIONTRANSPORTANIMAL);
        return transportAnimal;
    }

    public SendMessage adoptionDocuments(long chatId) {
        SendMessage listDocsDecor = new SendMessage();
        listDocsDecor.setChatId(chatId);
        listDocsDecor.setText(ShelterInformationDirectory.LISTSDOCSDECOR);
        return listDocsDecor;
    }

    public SendMessage meetingAnimals(long chatId) {
        SendMessage shelterInfo = new SendMessage();
        shelterInfo.setChatId(chatId);
        shelterInfo.setText("Вы можете назначить встречу с нашими животными, связавшись с нами.");
        return shelterInfo;
    }

    public SendMessage getBreedDogAndColor(long chatId) {
        SendMessage getBreedDogAndColor = new SendMessage();
        getBreedDogAndColor.setChatId(chatId);
        getBreedDogAndColor.setText("Пожалуйста, укажите породу и предпочтительный окрас собаки, которую вы ищете.");
        return getBreedDogAndColor;
    }

    ///////////////
    public SendMessage getColorDogAndCat(long chatId) {
        SendMessage getColorDogAndCat = new SendMessage();
        getColorDogAndCat.setChatId(chatId);
        getColorDogAndCat.setText("Дайте нам знать предпочтения по цвету для собаки или кота, которого вы хотели бы усыновить.");
        return getColorDogAndCat;
    }

    public SendMessage getAgeDogAndCat(long chatId) {
        SendMessage getAgeDogAndCat = new SendMessage();
        getAgeDogAndCat.setChatId(chatId);
        getAgeDogAndCat.setText("Какой возрастной диапазон вы ищете у собаки или кошки?");
        return getAgeDogAndCat;
    }

    public SendMessage getNameDogAndCat(long chatId) {
        SendMessage getNameDogAndCat = new SendMessage();
        getNameDogAndCat.setChatId(chatId);
        getNameDogAndCat.setText("Пожалуйста, укажите имя, которое вы хотите дать собаке или кошке, которую вы усыновляете.");
        return getNameDogAndCat;
    }

    public SendMessage getAllDogAndCat(long chatId) {
        SendMessage getAllDogAndCat = new SendMessage();
        getAllDogAndCat.setChatId(chatId);
        getAllDogAndCat.setText("У нас есть разнообразие собак и кошек, доступных для усыновления. " +
                "Свяжитесь с нами для получения более подробной информации.");
        return getAllDogAndCat;
    }

    //////////////////////
    public SendMessage getContactVolunteer(long chatId) {
//        Записываем в лист всех полученных волонтеров
        List<User> volunteerList = userServiceImpl.getAllVolunteer();
//        Выбираем ChatId случайного волонтера
        Random rand = new Random();
        long randomVolunteer = volunteerList.get(rand.nextInt(volunteerList.size())).getChatId();
//        Создаем сообщение для волонтера с контактами пользователя
        SendMessage messageVolunteer = new SendMessage();
        messageVolunteer.setChatId(randomVolunteer);
        messageVolunteer.setText("Пользователь " + chatId + " телеграмм-бота просит написать ему");
//        Дальше надо разбираться, метод для отправки сообщений в классе TelegramBot, если на него сделать зависимость будет циклическая ссылка
//        надо искать в этом классе, он есть, так как сообщения отправляются
//        Должно быть что-то типа:
//        execute(messageVolunteer);

        SendMessage getContactVolunteer = new SendMessage();
        getContactVolunteer.setChatId(chatId);
        getContactVolunteer.setText("Наши добровольцы скоро свяжутся с вами");
//        getContactVolunteer.setText("Наши добровольцы готовы помочь вам. Свяжитесь с нами для возможностей добровольчества.");
        return getContactVolunteer;

    }

    public SendMessage leaveContact(long chatId) {
        SendMessage leaveContact = new SendMessage();
        leaveContact.setChatId(chatId);
        leaveContact.setText("Пожалуйста, оставьте ваш контактную информацию, чтобы мы могли связаться с вами.");
        return leaveContact;
    }

    public SendMessage shelterTB(long chatId) {
        SendMessage shelterTB = new SendMessage();
        shelterTB.setChatId(chatId);
        shelterTB.setText("Посетите наш приют, чтобы увидеть наших пушистых друзей лично.");
        return shelterTB;
    }

    public SendMessage registerPass(long chatId) {
        SendMessage registerPass = new SendMessage();
        registerPass.setChatId(chatId);
        registerPass.setText("Вы можете зарегистрироваться, чтобы получить пропуск для посещения нашего приюта.");
        return registerPass;
    }

    public SendMessage sendReport(long chatId) {
        SendMessage sendReport = new SendMessage();
        sendReport.setChatId(chatId);
        sendReport.setText("Если у вас есть какие-либо вопросы или нужно сообщить о проблеме, пожалуйста, свяжитесь с нами.");
        return sendReport;
    }

    ////////
    public SendMessage InfoShelterTimeAndAddress(long chatId) {
        SendMessage timeAndAddress = new SendMessage();
        timeAndAddress.setChatId(chatId);
        timeAndAddress.setText(ShelterInformationDirectory.SHELTERADRESS + ShelterInformationDirectory.WORKTIME);
        return timeAndAddress;
    }

    public SendMessage infoShelter(long chatId) {
        SendMessage shelterInfo = new SendMessage();
        shelterInfo.setChatId(chatId);
        shelterInfo.setText(ShelterInformationDirectory.SHELTERINFO);
        return shelterInfo;
    }
///////////

    /**
     * Стартовое меню
     *
     * @param chatId Id чата
     * @param name   имя пользователя
     * @return сообщение для пользователя
     */
    public SendMessage sendStartMenu(long chatId, String name) {
        log.info("Идет инициализация стартового меню... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Привет " + name + "! Добро пожаловать в приют пушистых друзей! \n" + "Я помогу вам найти верного друга!\n\n" + "Выберите интересующую вас кнопку меню \uD83D\uDC47");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём ряд
        rowList.add(List.of(createButton(BTN_ADMINISTRATION)));
        rowList.add(List.of(createButton(BTN_DOGS), createButton(BTN_CATS)));
        inlineKeyboardMarkup.setKeyboard(rowList);

        msg.setReplyMarkup(inlineKeyboardMarkup);

        return msg;
    }

    /**
     * Создаем меню администрации
     *
     * @param chatId Id чата
     * @return сообщение для администратора
     */
    public SendMessage administrationMenu(long chatId) {
        log.info("Идет инициализация меню администрации... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Вы вошли в администрацию, что вас интересует? \n");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём лист листов и закидываем списки рядов
        rowList.add(List.of(createButton(BTN_INFO_SHELTER), createButton(BTN_LOCATION)));
        rowList.add(List.of(createButton(BTN_SEND_REPORT), createButton(BTN_INFO_TAKE_ANIMAL)));
        rowList.add(List.of(createButton(BTN_GET_PASS), createButton(BTN_TB_RECOMMENDATION)));
        rowList.add(List.of(createButton(BTN_LEAVE_CONTACTS), createButton(BTN_HELP)));
        rowList.add(List.of(createButton(BTN_MAIN_MENU)));
        inlineKeyboardMarkup.setKeyboard(rowList); //Вносим настройки в клавиатуру

        msg.setReplyMarkup(inlineKeyboardMarkup); //Изменяем клавиатуру

        return msg; //Отправляем сообщение
    }

    /**
     * Создаем меню животных
     *
     * @param chatId идентификатор чата
     * @return отправляем ответ
     */
    public SendMessage dogsAndCatMenu(long chatId) {
        log.info("Идет инициализация меню животных... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Вы вошли в меню поиска друга, давайте вместе подберем.");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём лист листов и закидываем списки рядов
        rowList.add(List.of(createButton(BTN_SHOW_ALL)));
        rowList.add(List.of(createButton(BTN_FIND_BY_NICK), createButton(BTN_FIND_BY_AGE)));
        rowList.add(List.of(createButton(BTN_FIND_BY_COLOR), createButton(BTN_FIND_BY_BREED)));
        inlineKeyboardMarkup.setKeyboard(rowList); //Вносим настройки в клавиатуру

        msg.setReplyMarkup(inlineKeyboardMarkup); //Изменяем клавиатуру

        return msg; //Отправляем сообщение
    }

    /**
     * Создаем меню что нужно знать для взятия животного
     *
     * @param chatId идентификатор чата
     * @return отправляем ответ
     */
    public SendMessage instructionAdoptionMenu(long chatId) {
        log.info("Идет инициализация меню как взять животное ... ");

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Здесь находится вся информация о усыновлении животных. ");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём лист листов и закидываем списки рядов
        rowList.add(List.of(createButton(BTN_RULES_TO_MEETING), createButton(BTN_DOCUMENTS_LISTS)));
        rowList.add(List.of(createButton(BTN_TRANSPORT_RECOMMENDATION), createButton(BTN_HOME_FOR_CUB)));
        rowList.add(List.of(createButton(BTN_HOME_FOR_ADULT), createButton(BTN_HOME_FOR_DISABLE)));
        rowList.add(List.of(createButton(BTN_HANDLERS_CONTACT), createButton(BTN_HANDLERS_TIPS)));
        rowList.add(List.of(createButton(BTN_REFUSE_REASONS)));
        inlineKeyboardMarkup.setKeyboard(rowList); //Вносим настройки в клавиатуру

        msg.setReplyMarkup(inlineKeyboardMarkup); //Изменяем клавиатуру

        return msg; //Отправляем сообщение
    }

    /**
     * Возвращает кнопку, у которой текст и отклик совпадают
     *
     * @param text текст и отклик кнопки
     * @return кнопка
     */
    public InlineKeyboardButton createButton(String text) {
        InlineKeyboardButton btn = new InlineKeyboardButton();    //Создаем кнопку
        btn.setText(text);                                        //Текст самой кнопки
        btn.setCallbackData(text);                                //Отклик на нажатие кнопки
        return btn;
    }
}

