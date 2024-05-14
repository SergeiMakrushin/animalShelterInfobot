package com.skypro.animalShelterInfoBot.bot;

import com.skypro.animalShelterInfoBot.informationDirectory.ShelterInformationDirectory;
import com.skypro.animalShelterInfoBot.model.Animal;
import com.skypro.animalShelterInfoBot.model.User;
import com.skypro.animalShelterInfoBot.repositories.AnimalRepository;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import com.skypro.animalShelterInfoBot.service.AnimalServiceImpl;
import com.skypro.animalShelterInfoBot.service.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    static final Pattern EMAIL_PATTERN = Pattern.compile("([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})");
    static final Pattern COLOR_PATTERN = Pattern.compile("\\w*белый|серый|черный|рыжий|оранжевый|зеленый|красный|желтый|синий\\w*");
    static final Pattern BREED_PATTERN = Pattern.compile("\\w*шпиц|дог|пудель|чихуахуа|хаски|мейн-кун|сфинкс|сиамская|британская|египетская|бенгальская\\w*");
    static final Pattern MIN_MAX_PATTERN = Pattern.compile("\\b\\d+\\b");

    private boolean isDog;
    private boolean isCheckAge;
    private boolean isCheckColor;
    private boolean isCheckBreed;
    private boolean isCheckContact;


    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    AnimalServiceImpl animalServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnimalRepository animalRepository;

    /**
     * Слушатель для отправки сообщений
     */
    @Setter
    private Listener listener;

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
            isCheckContact = false;
            isCheckColor = false;
            isCheckAge = false;
            isCheckBreed = false;
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String msgText = callbackQuery.getData();
            long chatId = callbackQuery.getMessage().getChatId();
            String name = callbackQuery.getFrom().getFirstName();
            String userName = callbackQuery.getFrom().getUserName();
            String surname = callbackQuery.getFrom().getLastName();
            textToSend = processingTextAndCallbackQuery(chatId, msgText, name, userName, surname);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            String msgText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
            String userName = update.getMessage().getChat().getUserName();
            String surname = update.getMessage().getChat().getLastName();
            textToSend = processingTextAndCallbackQuery(chatId, msgText, name, userName, surname);
        }
        return textToSend;
    }

    /**
     * Обработка входящих сообщений
     * Обработка нажатий кнопок меню
     *
     * @param chatId   Id чата
     * @param text     текст, который пришел от пользователя
     * @param name     имя пользователя
     * @param userName Ник пользователя
     * @param surname  Фамилия пользователя
     * @return сообщение для пользователя
     */
    private SendMessage processingTextAndCallbackQuery(long chatId, String text, String name, String userName, String surname) {
        log.info("Нажата клавиша \"" + text + "\"");

        if (BTN_DOGS.equals(text) || CMD_DOGS.equals(text)) {
            isDog = true;
            return dogsAndCatMenu(chatId);
        } else if (BTN_CATS.equals(text) || CMD_CATS.equals(text)) {
            isDog = false;
            return dogsAndCatMenu(chatId);
        }

        return switch (text) {
            case BTN_ADMINISTRATION -> administrationMenu(chatId);
            case BTN_INFO_SHELTER, CMD_INFO_SHELTER -> infoShelter(chatId);
            case BTN_LOCATION, CMD_LOCATION -> InfoShelterTimeAndAddress(chatId);
            case BTN_SEND_REPORT, CMD_SEND_REPORT -> sendReport(chatId);
            case BTN_INFO_TAKE_ANIMAL, CMD_INFO_TAKE_ANIMAL -> instructionAdoptionMenu(chatId);
            case BTN_GET_PASS, CMD_GET_PASS -> registerPass(chatId);
            case BTN_TB_RECOMMENDATION, CMD_TB_RECOMMENDATIONS -> shelterTB(chatId);
            case BTN_LEAVE_CONTACTS, CMD_LEAVE_CONTACT -> leaveContact(chatId, text);
            case BTN_HELP, CMD_HELP -> getContactVolunteer(chatId, userName);
            case BTN_SHOW_ALL -> getAllDogAndCat(chatId);
            case BTN_FIND_BY_NICK -> getNameDogAndCat(chatId);
            case BTN_FIND_BY_AGE -> getAgeDogAndCat(chatId);
            case BTN_FIND_BY_COLOR -> getColorDogAndCat(chatId);
            case BTN_FIND_BY_BREED -> getBreedDogAndCat(chatId);
            case BTN_RULES_TO_MEETING -> meetingAnimals(chatId);
            case BTN_DOCUMENTS_LISTS -> adoptionDocuments(chatId);
            case BTN_TRANSPORT_RECOMMENDATION -> recommendationTransportAnimal(chatId);
            case BTN_HOME_FOR_CUB -> homeForChild(chatId);
            case BTN_HOME_FOR_ADULT -> homeForAdults(chatId);
            case BTN_HOME_FOR_DISABLE -> homeForLimitedOpportunities(chatId);
            case BTN_HANDLERS_CONTACT -> getContactDogHandlers(chatId);
            case BTN_HANDLERS_TIPS -> adviceDogHandlers(chatId);
            case BTN_REFUSE_REASONS -> reasonsForRefusal(chatId);
            case BTN_MAIN_MENU, CMD_START -> sendStartMenu(chatId, name, surname);
            default -> checkingTextForContacts(chatId, text, name, surname, userName);
        };
    }

    /**
     * Метод обрабатывает сообщение если была нажата кнопка "оставить контакты"
     * И если сообщение соответствует номеру телефона и емайл адресу
     * то сохраняет пользователя в базу данных и отправляет уведомление волонтеру
     * если не соответствует, используется внешний метод обработки
     * который ищет соответствие текста с цветом
     *
     * @param chatId  идентификатор чата
     * @param text    текст сообщения
     * @param name    имя отправителя
     * @param surname фамилия отправителя
     * @return отправка сообщения или переход обработки на другой метод
     */
    public SendMessage checkingTextForContacts(long chatId, String text, String name, String surname, String userName) {
        log.info("метод обработки и сохранения в бд запущен");
        Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(text);
        Matcher emailMatcher = EMAIL_PATTERN.matcher(text);

        if (phoneMatcher.find() && emailMatcher.find() && isCheckContact) {
            String phoneNumber = phoneMatcher.group();
            String email = emailMatcher.group(1);
            userServiceImpl.updateUser(chatId, new User(null, chatId, name, surname, null, phoneNumber, email, false, null));

            getContactVolunteer(chatId, userName);
            log.info("Данные сохранены, и отправлены волонтеру");

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(ShelterInformationDirectory.REPLYTOCONTACT);
            return sendMessage;
        }
        log.info("В тексте не найдено совподения с номером телефона и емайл почтой, переход на внешний обработчик");
        return checkingTextForColor(chatId, text);
    }

    /**
     * метод обрабатывает сообщение если была нажата кнопка "найти по цвету"
     * ищет соответствие с цветом животного и отвечает
     * информацией о животных с введенным цветом
     * если не соответствует, используется внешний метод обработки
     * который ищет соответствие с возрастным диапозоном
     *
     * @param chatId идентификатор чата
     * @param text   текст сообщения
     * @return отправка ответа
     */
    public SendMessage checkingTextForColor(long chatId, String text) {
        log.info("Метод обработки и поиска животного по цвету запущен");
        Matcher colorPets = COLOR_PATTERN.matcher(text);
        SendMessage getColorDogAndCat = new SendMessage();
        getColorDogAndCat.setChatId(chatId);

        if (isDog && colorPets.find() && isCheckColor) {
            String color = colorPets.group();
            String dogByColor = animalServiceImpl.findDogByColor(color);
            if (dogByColor.isEmpty()) {
                getColorDogAndCat.setText("Собаки с таким цветом не найдены!");
                return getColorDogAndCat;
            }
            getColorDogAndCat.setText("Вот кого удалось найти\uD83D\uDC36: \n\n" + dogByColor);
            log.info("Все собаки найдены");
            return getColorDogAndCat;
        } else if (!isDog && colorPets.find() && isCheckColor) {
            String color = colorPets.group();
            String catByColor = animalServiceImpl.findCatByColor(color);
            if (catByColor.isEmpty()) {
                getColorDogAndCat.setText("Кошек с таким цветом не найдено!");
                return getColorDogAndCat;
            }
            getColorDogAndCat.setText("Вот кого удалось найти\uD83D\uDE3A: \n\n" + catByColor);
            log.info("Все кошки найдены");
            return getColorDogAndCat;
        } else
            log.info("В тексте не найдено совпадения с цветом животного");
        return checkingTextForAgeBetween(chatId, text);
    }

    /**
     * метод обрабатывает сообщение если была нажата кнопка "найти по возрасту"
     * ищет соответствие с минимальными цифрами и максимальными
     * в соответствии с полученными данными, отправляется список
     * животных которые по возрасту входят в этот диапазон
     * если сообщение не соответствует паттерну чисел
     * используется внешний метод обработки
     * который ищет соответствие с породой
     *
     * @param chatId идентификатор чата
     * @param text   текст сообщения
     * @return отправка ответа
     */
    public SendMessage checkingTextForAgeBetween(long chatId, String text) {
        log.info("Метод обработки и поиска животного по возрасту запущен");
        Matcher agePets = MIN_MAX_PATTERN.matcher(text);
        int minAge = Integer.MAX_VALUE;
        int maxAge = Integer.MIN_VALUE;
        while (agePets.find()) {
            int age = Integer.parseInt(agePets.group());
            if (age < minAge) {
                minAge = age;
            }
            if (age > maxAge) {
                maxAge = age;
            }
        }
        log.info("значение minAge = " + minAge + " Значение maxAge = " + maxAge);

        SendMessage getAgeBetweenDogAndCat = new SendMessage();
        getAgeBetweenDogAndCat.setChatId(chatId);

        if (isDog && isCheckAge) {
            String dogByAgeBetween = animalServiceImpl.findDogByAgeBetween(minAge, maxAge);
            if (dogByAgeBetween.isEmpty()) {
                getAgeBetweenDogAndCat.setText("В данном диапазоне собак не найдено!");
                return getAgeBetweenDogAndCat;
            }
            getAgeBetweenDogAndCat.setText("Вот кого удалось найти\uD83D\uDC36: \n\n" + dogByAgeBetween);
            return getAgeBetweenDogAndCat;
        } else if (!isDog && isCheckAge) {
            String catByAgeBetween = animalServiceImpl.findCatByAgeBetween(minAge, maxAge);
            if (catByAgeBetween.isEmpty()) {
                getAgeBetweenDogAndCat.setText("В данном диапазоне кошек не найдено!");
                return getAgeBetweenDogAndCat;
            }
            getAgeBetweenDogAndCat.setText("Вот кого удалось найти\uD83D\uDE3A: \n\n" + catByAgeBetween);
            return getAgeBetweenDogAndCat;
        } else
            log.info("В тексте не найдено совпадений с диапазоном возраста");
        return checkingTextForBreed(chatId,text);
    }

    /**
     * метод обрабатывает сообщение если была нажата кнопка "найти по породе"
     * ищет соответствие с породой животного и отвечает
     * информацией о животных с введенной породой
     * если не соответствует, вызывается метод
     * с дефолтным ответом
     *
     * @param chatId идентификатор чата
     * @param text   текст сообщения
     * @return отправка ответа
     */
    public SendMessage checkingTextForBreed(long chatId, String text) {
        log.info("Метод обработки и поиска животного по породе запущен");
        Matcher breedPets = BREED_PATTERN.matcher(text);
        SendMessage getBreedDogAndCat = new SendMessage();
        getBreedDogAndCat.setChatId(chatId);

        if (isDog && breedPets.find() && isCheckBreed) {
            String breed = breedPets.group();
            String dogByBreed = animalServiceImpl.findDogByBreed(breed);
            if (dogByBreed.isEmpty()) {
                getBreedDogAndCat.setText("Собаки с такой породой не найдены!");
                return getBreedDogAndCat;
            }
            getBreedDogAndCat.setText("Вот кого удалось найти\uD83D\uDC36: \n\n" + dogByBreed);
            log.info("Все собаки найдены");
            return getBreedDogAndCat;
        } else if (!isDog && breedPets.find() && isCheckBreed) {
            String breed = breedPets.group();
            String catByBreed = animalServiceImpl.findCatByBreed(breed);
            if (catByBreed.isEmpty()) {
                getBreedDogAndCat.setText("Кошек с такой породой не найдено!");
                return getBreedDogAndCat;
            }
            getBreedDogAndCat.setText("Вот кого удалось найти\uD83D\uDE3A: \n\n" + catByBreed);
            log.info("Все кошки найдены");
            return getBreedDogAndCat;
        } else
            log.info("В тексте не найдено совпадения с породой животного");
        return settingSendMessage(chatId, ShelterInformationDirectory.NOTFOUNDTEXT);
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
        shelterInfo.setText(ShelterInformationDirectory.MEETINGANIMALS);
        return shelterInfo;
    }

    public SendMessage getBreedDogAndCat(long chatId) {
        SendMessage getBreedDogAndCat = new SendMessage();
        getBreedDogAndCat.setChatId(chatId);
        isCheckBreed = true;
        if (isDog) {
            getBreedDogAndCat.setText("Напишите, какая порода собаки вас интересует\uD83D\uDC36");
        } else if (!isDog) {
            getBreedDogAndCat.setText("Напишите, какая порода кошки вас интересует\uD83D\uDC36");
        }
        return getBreedDogAndCat;
    }

    public SendMessage getColorDogAndCat(long chatId) {
        SendMessage getColorDogAndCat = new SendMessage();
        getColorDogAndCat.setChatId(chatId);
        isCheckColor = true;
        if (isDog) {
            getColorDogAndCat.setText("Напишите, какой цвет собаки вас интересует\uD83D\uDC36");
        } else if (!isDog) {
            getColorDogAndCat.setText("Напишите, какой цвет кошки вас интересует\uD83D\uDE3A");
        }
        return getColorDogAndCat;
    }

    public SendMessage getAgeDogAndCat(long chatId) {
        SendMessage getAgeDogAndCat = new SendMessage();
        getAgeDogAndCat.setChatId(chatId);
        isCheckAge = true;
        if (isDog) {
            getAgeDogAndCat.setText("В каком возрастном диапазоне вы ищете собак?\n" +
                    "Напишите диапозон поиска, например от 5 до 12 ⬇\uFE0F");
        } else if (!isDog) {
            getAgeDogAndCat.setText("В каком возрастном диапазоне вы ищете кошек?\n" +
                    "Напишите диапозон поиска, например от 5 до 12 ⬇\uFE0F");
        }
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
        if (isDog) {
            List<Animal> dogs = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.DOG);
            getAllDogAndCat.setText(dogs.toString().replace("[", "").replace("]", "").replace(",", ""));
        } else {
            List<Animal> cats = animalRepository.findByCatOrDog(Animal.TapeOfAnimal.CAT);
            getAllDogAndCat.setText(cats.toString().replace("[", "").replace("]", "").replace(",", ""));
        }
        return getAllDogAndCat;
    }

    public SendMessage getContactVolunteer(long chatId, String userName) {
        List<User> volunteerList = userServiceImpl.getAllVolunteer();   // Записываем в лист всех полученных волонтеров
        Random rand = new Random();                                     // Выбираем ChatId случайного волонтера
        long randomVolunteer = volunteerList.get(rand.nextInt(volunteerList.size())).getChatId();

        SendMessage messageVolunteer = new SendMessage();  // Создаем сообщение для волонтера с контактами пользователя
        messageVolunteer.setChatId(randomVolunteer);
        messageVolunteer.setText("Пользователь tg://resolve?domain=" + userName
                + " телеграмм-бота хочет связаться с вами или оставил контактные данные и ждет звонка!");
        listener.sendMessage(messageVolunteer);     // Отправляем сообщение волонтеру

        SendMessage getContactVolunteer = new SendMessage();
        getContactVolunteer.setChatId(chatId);
        getContactVolunteer.setText("""
                Наши добровольцы скоро свяжутся с вами!

                отправте что нибудь боту, для открытия дополнительного меню, что бы продолжить диалог с ботом!""");

        return getContactVolunteer;

    }

    public SendMessage leaveContact(long chatId, String text) {
        log.info("метод получения и обработки сообщения");
        SendMessage leaveContact = new SendMessage();
        leaveContact.setChatId(chatId);
        leaveContact.setText(ShelterInformationDirectory.LEAVECONTACT);
        isCheckContact = true;
        return leaveContact;
    }

    public SendMessage shelterTB(long chatId) {
        SendMessage shelterTB = new SendMessage();
        shelterTB.setChatId(chatId);
        shelterTB.setText(ShelterInformationDirectory.TBSHELTER);
        return shelterTB;
    }

    public SendMessage registerPass(long chatId) {
        SendMessage registerPass = new SendMessage();
        registerPass.setChatId(chatId);
        registerPass.setText(ShelterInformationDirectory.REGISTERPASS);
        return registerPass;
    }

    public SendMessage sendReport(long chatId) {
        SendMessage sendReport = new SendMessage();
        sendReport.setChatId(chatId);
        sendReport.setText("Если у вас есть какие-либо вопросы или нужно сообщить о проблеме, пожалуйста, свяжитесь с нами.");
        return sendReport;
    }

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

    /**
     * Стартовое меню
     *
     * @param chatId Id чата
     * @param name   имя пользователя
     * @return сообщение для пользователя
     */
    public SendMessage sendStartMenu(long chatId, String name, String surname) {
        log.info("Идет инициализация стартового меню... ");
        registerUserAndWelcome(chatId, name, surname);

        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText("Вы в главном меню!\n" +
                "Выберите интересующую вас кнопку \uD83D\uDC47");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //Создаем объект разметки клавиатуры

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); //Создаём ряд
        rowList.add(List.of(createButton(BTN_ADMINISTRATION)));
        rowList.add(List.of(createButton(BTN_DOGS), createButton(BTN_CATS)));
        inlineKeyboardMarkup.setKeyboard(rowList);

        msg.setReplyMarkup(inlineKeyboardMarkup);

        return msg;
    }

    /**
     * Проверяем наличие пользователя в БД
     * Если нет, сохраняем и приветствуем
     * Если да, то метод ничего не возвращает
     *
     * @param chatId  идентификатор чата
     * @param name    имя пользователя
     * @param surname фамилия пользователя
     */
    public void registerUserAndWelcome(long chatId, String name, String surname) {
        log.info("Проверяется наличие пользователя в БД");

        if (userRepository.findUserByChatId(chatId) == null) {
            log.info("Пользователя нет, сохраняем его");
            userRepository.save(new User(null, chatId, name, surname, null, null, null, false, null));
            SendMessage msg = new SendMessage();
            msg.setChatId(chatId);
            msg.setText("Привет " + name + "! Добро пожаловать в приют пушистых друзей! \n"
                    + "Я помогу вам найти верного друга!");
            listener.sendMessage(msg);
        } else {
            log.info("Пользователь уже есть, ничего не возвращается");
        }
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

    public void setUserServiceImpl(UserServiceImpl mock) {

    }

    public void setAnimalServiceImpl(Object mock) {

    }

    public void setUserRepository(UserRepository mock) {

    }
}

