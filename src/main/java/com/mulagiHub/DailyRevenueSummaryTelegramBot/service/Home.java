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
        } else if(update.getMessage().getText().equals("Home \uD83C\uDFE0") || update.getMessage().getText().equals("cancel")) {
            message.setText(backHome);
        }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Register  \uD83C\uDD95"));


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Send money \uD83D\uDCB0"));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("withdraw money \uD83C\uDFE6"));

        KeyboardRow keyboardForthRow = new KeyboardRow();
        keyboardForthRow.add(new KeyboardButton("Deposit \uD83D\uDCB1"));


        KeyboardRow keyboardFirthRow = new KeyboardRow();
        keyboardFirthRow.add(new KeyboardButton("Buy Airtime \uD83D\uDCF1"));

        KeyboardRow keyboardSixthRow = new KeyboardRow();
        keyboardSixthRow.add(new KeyboardButton("Buy and for goods and service \uD83D\uDECD️"));

        KeyboardRow keyboardSevenRow = new KeyboardRow();
        keyboardSixthRow.add(new KeyboardButton("Account \uD83C\uDFE0"));

        // Add all of the keyboard rows to the list

        if(Boolean.TRUE.equals(customerService.existsByTelegramUserId(update.getMessage().getChat().getId()))){
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);
            keyboard.add(keyboardForthRow);
            keyboard.add(keyboardFirthRow);
            keyboard.add(keyboardSixthRow);
            keyboard.add(keyboardSevenRow);
        }
        else {
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);
            keyboard.add(keyboardForthRow);
            keyboard.add(keyboardFirthRow);
            keyboard.add(keyboardSixthRow);
            keyboard.add(keyboardSevenRow);
        }


        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return  message;

    }
}
