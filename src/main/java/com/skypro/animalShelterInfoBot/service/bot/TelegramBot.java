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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final InfoBotConfiguration config;
    private final BotService botService;

    public TelegramBot(InfoBotConfiguration config, BotService botService) {
        this.config = config;
        this.botService = botService;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Начать диалог с ботом."));
        listOfCommands.add(new BotCommand("/info_shelter", "Общая информация о приюте"));
        listOfCommands.add(new BotCommand("/info_take_animal", "Инструкция - как приютить животное"));
        listOfCommands.add(new BotCommand("/send_report", "прислать отчет"));
        listOfCommands.add(new BotCommand("/leave_contact", "Оставить контакты для связи"));
        listOfCommands.add(new BotCommand("/help", "Позвать волонтера"));
        listOfCommands.add(new BotCommand("/get_pass", "Получить контакт охраны, для регистрации пропуска"));
        listOfCommands.add(new BotCommand("/tb_recommendations", "ТБ нахождения на территории приюта"));
        listOfCommands.add(new BotCommand("/location", "Расписание работы приюта, адрес и схема проезда"));
        listOfCommands.add(new BotCommand("/dogs", "Собачий отдел"));
        listOfCommands.add(new BotCommand("/cats", "Кошачий отдел"));
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
                SendMessage sendMessage = botService.inputMsg(update);
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
