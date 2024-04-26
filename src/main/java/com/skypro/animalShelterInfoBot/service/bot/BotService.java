package com.skypro.animalShelterInfoBot.service.bot;

import com.skypro.animalShelterInfoBot.informationDirectory.ShelterInformationDirectory;
import com.skypro.animalShelterInfoBot.model.human.ChatUser;
import com.skypro.animalShelterInfoBot.services.UserService;
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
public class BotService {

    private final String BTN_ADMINISTRATION = "Администрация";
    private final String BTN_DEPARTMENT_DOGS = "Отдел собак";
    private final String BTN_DEPARTMENT_CATS = "Отдел кошек";
    private final String BTN_SHELTER_INFO = "Информация о приюте";
    private final String BTN_HOURS_N_ADDRESS = "Часы работы, адрес";
    private final String BTN_SEND_REPORT = "Прислать отчет";
    private final String BTN_HOW_TO_GET = "Как получить питомца";
    private final String BTN_GET_PASS = "Получить пропуск";
    private final String BTN_SAFETY_INSTRUCTIONS = "ТБ на территории";
    private final String BTN_LEAVE_CONTACTS = "Оставить контакты";
    private final String BTN_CALL_VOLUNTEER = "позвать волонтера";
    private final String BTN_MAIN_MENU = "На главное меню";
    private final String BTN_SHOW_ALL = "Показать всех";
    private final String BTN_FIND_BY_NICK = "Найти по кличке";
    private final String BTN_FIND_BY_AGE = "Найти по возрасту";
    private final String BTN_FIND_BY_COLOR = "Найти по окрасу";
    private final String BTN_FIND_BY_BREED = "Найти по породе";
    private final String BTN_RULES_TO_MEETING = "Правила знакомства с животным";
    private final String BTN_DOCUMENTS_LISTS = "Список документов для оформления";
    private final String BTN_TRANSPORT_RECOMMENDATION = "Рекомендации транспортировки";
    private final String BTN_HOME_FOR_CUB = "обустройство дома для детеныша";
    private final String BTN_HOME_FOR_ADULT = "обустройство дома для взрослого питомца";
    private final String BTN_HOME_FOR_DISABLE = "обустройство дома для питомца с огр возможностями";
    private final String BTN_HANDLERS_CONTACT = "Получить контакты кинологов";
    private final String BTN_HANDLERS_TIPS = "Советы кинолога";
    private final String BTN_REFUSE_REASONS = "Причины отказа";

    @Autowired
    UserService userService;

    /**
     * Создаем List с названием всех кнопок меню
     */
    private final List<String> NAME_BUTTONS = new ArrayList<>(List.of(
            //Стартовое меню
            // индекс 0 - 1 - 2
            BTN_ADMINISTRATION, BTN_DEPARTMENT_DOGS, BTN_DEPARTMENT_CATS,

            //Меню администрация
            // индекс 3 - 4
            BTN_SHELTER_INFO, BTN_HOURS_N_ADDRESS,
            // индекс 5 - 6
            BTN_SEND_REPORT, BTN_HOW_TO_GET,
            // индекс 7 - 8
            BTN_GET_PASS, BTN_SAFETY_INSTRUCTIONS,
            // индекс 9 - 10
            BTN_LEAVE_CONTACTS, BTN_CALL_VOLUNTEER,
            // индекс 11
            BTN_MAIN_MENU,

            //меню Отдел собак (Отдел кошек)
            // индекс 12 - 13
            BTN_SHOW_ALL, BTN_FIND_BY_NICK,
            // индекс 14 - 15
            BTN_FIND_BY_AGE, BTN_FIND_BY_COLOR,
            // индекс 16
            BTN_FIND_BY_BREED,

            //меню взятия животного
            // индекс 17 - 18
            BTN_RULES_TO_MEETING, BTN_DOCUMENTS_LISTS,
            // индекс 19 - 20
            BTN_TRANSPORT_RECOMMENDATION, BTN_HOME_FOR_CUB,
            // индекс 21 - 22
            BTN_HOME_FOR_ADULT, BTN_HOME_FOR_DISABLE,
            // индекс 23 - 24
            BTN_HANDLERS_CONTACT, BTN_HANDLERS_TIPS,
            // индекс 25
            BTN_REFUSE_REASONS));

    /**
     * Создаем постоянную клавиатуру
     *
     * @param chatId Id чата
     * @param text Текст сообщения
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
     * @param chatId Id чата
     * @param text текст, который пришел от пользователя
     * @param name имя пользователя
     * @return сообщение для пользователя
     */
    SendMessage processingTextAndCallbackQuery(long chatId, String text, String name) {
        //проходим по индексам кнопок
        int indexButton = -1;
        for (int i = 0; i < NAME_BUTTONS.size(); i++) {
            if (NAME_BUTTONS.get(i).equalsIgnoreCase(text)) {
                indexButton = i;
                break;
            }
        }

        log.info("Нажата клавиша " + indexButton);

        SendMessage textToSend;

        //Команды кнопок
        textToSend = switch (indexButton) {
            case 0 -> administrationMenu(chatId);
            case 1, 2 -> dogsAndCatMenu(chatId);            //Настроить логику выбора (Кошки и собаки)
            case 3 -> infoShelter(chatId);
            case 4 -> InfoShelterTimeAndAddress(chatId);
            case 5 -> sendReport(chatId);
            case 6 -> instructionAdoptionMenu(chatId);
            case 7 -> registerPass(chatId);
            case 8 -> shelterTB(chatId);
            case 9 -> leaveContact(chatId);
            case 10 -> getContactVolunteer(chatId);
            case 11 -> sendStartMenu(chatId, name);         //Написать логику, если пользователь уже есть в БД - не приветствовать
            case 12 -> getAllDogAndCat(chatId);
            case 13 -> getNameDogAndCat(chatId);
            case 14 -> getAgeDogAndCat(chatId);
            case 15 -> getColorDogAndCat(chatId);
            case 16 -> getBreedDogAndColor(chatId);
            case 17 -> meetingAnimals(chatId);
            case 18 -> listDocsDecor(chatId);
            case 19 -> recommendationTransportAnimal(chatId);
            case 20 -> homeForChild(chatId);
            case 21 -> homeForAdults(chatId);
            case 22 -> homeForLimitedOpportunities(chatId);
            case 23 -> getContactDogHandlers(chatId);
            case 24 -> adviceDogHandlers(chatId);
            case 25 -> reasonsForRefusal(chatId);
            default -> settingSendMessage(chatId, "Выберите интересующую вас кнопку меню \uD83D\uDC47 \n" +
                    "Или воспользуйтесь кнопкой вызова волонтера, он вам поможет \uD83D\uDE09");
        };

        // Текстовые команды
        textToSend = switch (text) {
            case "/start" -> sendStartMenu(chatId, name);
            case "/info_shelter" -> infoShelter(chatId);
            case "/info_take_animal" -> instructionAdoptionMenu(chatId);
            case "/send_report" -> sendReport(chatId);
            case "/leave_contact" -> leaveContact(chatId);
            case "/help" -> getContactVolunteer(chatId);
            case "/get_pass" -> registerPass(chatId);
            case "/tb_recommendations" -> shelterTB(chatId);
            case "/location" -> InfoShelterTimeAndAddress(chatId);
            case "/dogs", "/cats" -> dogsAndCatMenu(chatId);
            default -> textToSend;
        };

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
//        Записываем в лист всех полученных волонтеров
        List<ChatUser> volunteerList = userService.getAllVolunteer();
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
     * @param chatId Id чата
     * @param name имя пользователя
     * @return сообщение для пользователя
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

        inlineKeyboardButton1.setText(BTN_ADMINISTRATION); //Текст самой кнопки
        inlineKeyboardButton1.setCallbackData(getBTN_ADMINISTRATION()); //Отклик на нажатие кнопки
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
     * @param chatId Id чата
     * @return сообщение для администратора
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

    SendMessage dogsAndCatMenu(long chatId) {
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

    SendMessage instructionAdoptionMenu(long chatId) {
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

