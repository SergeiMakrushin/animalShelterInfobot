package com.skypro.animalShelterInfoBot.bot;

import com.skypro.animalShelterInfoBot.configuration.InfoBotConfiguration;
import com.skypro.animalShelterInfoBot.model.User;
import com.skypro.animalShelterInfoBot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot implements BotService.Listener {
    private final InfoBotConfiguration config;
    private final BotService botService;
    @Autowired
    UserRepository userRepository;



    public TelegramBot(InfoBotConfiguration config, BotService botService) {
        this.config = config;
        this.botService = botService;


        botService.setListener(this);

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_START, "Начать диалог с ботом."));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_INFO_SHELTER, "Общая информация о приюте"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_INFO_TAKE_ANIMAL, "Инструкция - как приютить животное"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_SEND_REPORT, "прислать отчет"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_LEAVE_CONTACT, "Оставить контакты для связи"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_HELP, "Позвать волонтера"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_GET_PASS, "Получить контакт охраны, для регистрации пропуска"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_TB_RECOMMENDATIONS, "ТБ нахождения на территории приюта"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_LOCATION, "Расписание работы приюта, адрес и схема проезда"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_DOGS, "Собачий отдел"));
        listOfCommands.add(new BotCommand(BotServiceImpl.CMD_CATS, "Кошачий отдел"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка списка команд бота: " + e.getMessage());
        }
    }

    private static final Pattern CONTACT_PATTERN = Pattern.compile("(\"^(\\\\+7)([0-9]{10})$\")");


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public String getBotToken() {
        return config.getToken();
    }

    /**
     * метод получения и обработки сообщения.
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        log.info("метод получения и обработки сообщения");
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        Matcher matcher = CONTACT_PATTERN.matcher(text);
        try {
            if (update.hasMessage() && update.getMessage().hasText() || update.hasCallbackQuery()) {
                SendMessage sendMessage = botService.inputMsg(update);
                sendMessage(sendMessage);
            } else if (matcher.matches()) {
                String phoneNumber = matcher.group(1);
                userRepository.save(new User(null, chatId, null, null,
                        null, phoneNumber, null, null, null));
                log.info("положили");
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Контакты сохранены");
                execute(sendMessage);
            }

        } catch (Exception e) {
            log.error("Ошибка в методе onUpdateReceived: " + e.getMessage());
        }
    }

    /**
     * Метод отправляет сообщение пользователю
     * в параметр принимает класс SendMessage
     *
     * @param message
     */
    public void sendMessage(SendMessage message) {
        try {
            if (!message.getText().isEmpty()) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения в методе sendMessage: " + e.getMessage());
        }
    }

}
