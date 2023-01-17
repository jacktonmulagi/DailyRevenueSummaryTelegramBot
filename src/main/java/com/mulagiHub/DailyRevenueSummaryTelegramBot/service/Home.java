package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.mulagiHub.DailyRevenueSummaryTelegramBot.constants.Messages.backHome;
import static com.mulagiHub.DailyRevenueSummaryTelegramBot.constants.Messages.welcomeNote;


@Component
public class Home {
    @Autowired
    SessionService sessionService;
    @Autowired
    CustomerService customerService;

    public SendMessage registerOrDefault(Update update) {

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        if (update.getMessage().getText().equals("/start")) {
            message.setText(welcomeNote);
        } else if (update.getMessage().getText().equals("Home \uD83C\uDFE0") || update.getMessage().getText().equals("cancel")) {
            message.setText(backHome);
        }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Enroll  \uD83C\uDD95"));


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Unsubscribe \uD83D\uDCB0"));
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Search  \uD83D\uDCC5"));




        // Add all of the keyboard rows to the list

        if (Boolean.TRUE.equals(customerService.existsByTelegramUserId(update.getMessage().getChat().getId()))) {
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);

        } else {
            keyboard.add(keyboardFirstRow);

        }


        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;

    }
}
