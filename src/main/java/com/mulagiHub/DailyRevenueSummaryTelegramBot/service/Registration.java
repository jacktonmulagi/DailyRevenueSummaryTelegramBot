package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.SessionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.GetCustomerName;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class Registration {
    @Autowired
    SessionUtil sessionUtil;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    GetCustomerName getCustomerName;
    public SendMessage newUser(Update update) {
        Session newSession =  sessionUtil.createNewSession(update.getMessage().getChat().getId(), SessionStage.CUSTOMER_REGISTRATION_INITIATION,
                SessionStage.CUSTOMER_REGISTRATION_ENTER_PHONE_NUMBER, TransactionType.REGISTRATION);
        sessionHandler.saveSession(newSession);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        if(timeOfDay < 12){
            message.setText("Good Morning  \uD83D\uDE0A   " + getCustomerName.customerName(update)+"  ? Kindly click on Share my number button to continue  ⬇️" );
        }else if(timeOfDay < 16){
            message.setText("Good Afternoon \uD83D\uDE0A " +  getCustomerName.customerName(update)+"  ? Kindly click on Share my number button to continue  ⬇️" );
        }else if(timeOfDay < 21){
            message.setText("Good Evening \uD83D\uDE0A  " + getCustomerName.customerName(update)+" ? Kindly click on Share my number button to continue ⬇️" );
        }else {
            message.setText("Good Night  \uD83D\uDE0A  "+  getCustomerName.customerName(update)+" ?Kindly click on Share my number button to continue ⬇️");
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Share my number  \uD83D\uDCF1");
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);




        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return  message;

    }


    public SendMessage userExists(Update update) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        if(timeOfDay < 12){
            message.setText("Good Morning  \uD83D\uDE0A   " + update.getMessage().getChat().getUserName()+"  ? You are already register kindly select other service️" );
        }else if(timeOfDay < 16){
            message.setText("Good Afternoon \uD83D\uDE0A " + update.getMessage().getChat().getUserName()+"  ? You are already register kindly select other service️" );
        }else if(timeOfDay < 21){
            message.setText("Good Evening \uD83D\uDE0A  " + update.getMessage().getChat().getUserName()+" ? You are already register kindly select other service" );
        }else {
            message.setText("Good Night  \uD83D\uDE0A  "+ update.getMessage().getChat().getUserName()+" ? You are already register kindly select other service️");
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();






        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Search  \uD83D\uDCC5"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Unsubscribe \uD83D\uDEAE"));

//        KeyboardRow keyboardForthRow = new KeyboardRow();
//        keyboardForthRow.add(new KeyboardButton("Deposit \uD83D\uDCB1"));
//
//
//        KeyboardRow keyboardFirthRow = new KeyboardRow();
//        keyboardFirthRow.add(new KeyboardButton("Buy Airtime \uD83D\uDCF1"));
//
//        KeyboardRow keyboardSixthRow = new KeyboardRow();
//        keyboardSixthRow.add(new KeyboardButton("Buy and for goods and service \uD83D\uDECD️"));
//
//        KeyboardRow keyboardSevenRow = new KeyboardRow();
//        keyboardSixthRow.add(new KeyboardButton("Account \uD83C\uDFE0"));

        // Add all of the keyboard rows to the list
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
//        keyboard.add(keyboardForthRow);
//        keyboard.add(keyboardFirthRow);
//        keyboard.add(keyboardSixthRow);
//        keyboard.add(keyboardSevenRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return  message;

    }
}
