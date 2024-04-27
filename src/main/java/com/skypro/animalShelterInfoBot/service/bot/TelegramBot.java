package com.skypro.animalShelterInfoBot.service.bot;

import com.skypro.animalShelterInfoBot.configuration.InfoBotConfiguration;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final InfoBotConfiguration config;
    private final BotServiceImpl botServiceImpl;

    public TelegramBot(InfoBotConfiguration config, BotServiceImpl botServiceImpl) {
        this.config = config;
        this.botServiceImpl = botServiceImpl;
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

        try {
            if (update.hasMessage() && update.getMessage().hasText() || update.hasCallbackQuery()) {
                SendMessage sendMessage = botServiceImpl.inputMsg(update);
                sendMessage(sendMessage);
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

//    private void mainMenu(long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Добро пожаловать в приют пушистых друзей! \n\n" +
//                "Выберите интересующую вас кнопку меню \uD83D\uDC47");
//
//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
//        var button = new InlineKeyboardButton();
//        var button1 = new InlineKeyboardButton();
//        var button2 = new InlineKeyboardButton();
//
//        button.setText("Администрация");
//        button.setCallbackData("АДМИНИСТРАЦИЯ");
//        button1.setText("отдел собак");
//        button1.setCallbackData("СОБАКИ");
//        button2.setText("отдел кошек");
//        button2.setCallbackData("КОШКИ");
//
//        rowInLine.add(button);
//        rowInLine.add(button1);
//        rowInLine.add(button2);
//
//        rowsInLine.add(rowInLine);
//
//        markupInLine.setKeyboard(rowsInLine);
//        message.setReplyMarkup(markupInLine);
//
////        try {
//            sendMessage(message);
    //   execute(message);
//        } catch (TelegramApiException e) {
//            log.error("Ошибка создания меню кнопок: " + e.getMessage());
//        }
//  }
}
