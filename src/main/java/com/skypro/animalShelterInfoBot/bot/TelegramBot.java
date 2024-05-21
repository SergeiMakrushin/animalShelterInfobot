package com.skypro.animalShelterInfoBot.bot;

import com.skypro.animalShelterInfoBot.configuration.InfoBotConfiguration;
import com.skypro.animalShelterInfoBot.model.PetReport;
import com.skypro.animalShelterInfoBot.repositories.PetReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot implements BotService.Listener {
    private final InfoBotConfiguration config;
    private final BotService botService;

    private final PetReportRepository petReportRepository;

    public TelegramBot(InfoBotConfiguration config, BotService botService, PetReportRepository petReportRepository) {
        this.config = config;
        this.botService = botService;
        this.petReportRepository = petReportRepository;


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
//
            if (update.hasMessage() && update.getMessage().hasText() || update.hasCallbackQuery() || update.getMessage().hasPhoto()) {
                log.info("проверка на пустоту");
                SendMessage sendMessage = botService.inputMsg(update);
                log.info("получение ответа от ботсервиса");
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

    public void sendMessage(SendPhoto sendPhoto) {

        try {

            execute(sendPhoto); // Call method to send the photo with caption
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void savingDatabase(PhotoSize photo, Long chatId, String messagePet, String userName) {
        GetFile getFile = new GetFile(photo.getFileId());
        try {
            File file = execute(getFile); //tg file obj
            System.out.println("file = " + file);
            System.out.println("file.getFilePath() = " + file.getFilePath());

//            получение массива байт и сохраняем в базу
            java.io.File file1 = downloadFile(file.getFilePath());

            byte[] bytes = new byte[(int) file1.length()];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file1);
                fis.read(bytes);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }

            PetReport petReport = new PetReport();
            petReport.setData(bytes);
            petReport.setUserChatId(chatId);
            petReport.setMessagePet(messagePet);
            petReport.setUserName(userName);

            petReportRepository.save(petReport);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

}
