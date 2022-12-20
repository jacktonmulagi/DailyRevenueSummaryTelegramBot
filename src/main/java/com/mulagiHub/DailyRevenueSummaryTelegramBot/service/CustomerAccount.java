package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.CustomerHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.EwalletHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.SessionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAccount {
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    CustomerHandler customerHandler;

    @Autowired
    EwalletHandler ewalletHandler;

    @Autowired
    SessionUtil sessionUtil;

    public SendMessage checkBalance(Update update) {
        Customer customer = customerHandler.findByTelegramUserId(update.getMessage().getChat().getId());
        Session newSession =  sessionUtil.createCheckBalanceSession(update.getMessage().getChat().getId(),customer.getMsisdn(), SessionStage.CONFIRM_CHECK_BALANCE_INITIATION,
                SessionStage.CONFIRM_CHECK_BALANCE_ENTER_PIN, TransactionType.BALANCE_INQUIRY);
        sessionHandler.saveSession(newSession);
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        message.setText("TO CHECK YOUR BALANCE ENTER YOUR 4 DIGIT PIN");
        keyboardFirstRow.add(new KeyboardButton("Home \uD83C\uDFE0"));
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;

    }


    public SendMessage checkBalanceEnterPin(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        Session session = sessionHandler.findSessionByChatIdAndNextConfirmCheckBalanceEnterPinAndPreviousConfirmCheckBalanceInitiation(update.getMessage().getChat().getId());
        System.out.println(session);
        Customer customer = customerHandler.findUserByMsisdn(session.getMsisdn());
        if (customer.getPin().equals(update.getMessage().getText())) {
            Float customerBalance = ewalletHandler.findEwalletByCustomerId(customer.getId()).getEBalance();
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            session.setNextStage(SessionStage.CLOSED);
            session.setPreviousStage(SessionStage.CONFIRM_CHECK_BALANCE_ENTER_PIN);
            session.setStatus(GeneralStatus.CLOSED);
            session.setLastUpdatedBy("app");
            session.setLastUpdatedAt(LocalDateTime.now());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String  now = LocalDateTime.now().format(dtf);
            message.setText("CONFIRM YOUR ACCOUNT BALANCE IS KSH  " + customerBalance + ". ON " + now);
            sessionHandler.saveSession(session);
            keyboardFirstRow.add(new KeyboardButton("Home \uD83C\uDFE0"));
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(replyKeyboardMarkup);
        } else {
            session.setNextStage(SessionStage.CLOSED);
            session.setPreviousStage(SessionStage.CONFIRM_CHECK_BALANCE_ENTER_PIN);
            session.setLastUpdatedBy("app");
            session.setLastUpdatedAt(LocalDateTime.now());
            session.setStatus(GeneralStatus.CLOSED);
            sessionHandler.saveSession(session);
            message.setText("YOU HAVE ENTERED THE WRONG PIN.PLEASE TRY AGAIN");
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("Home \uD83C\uDFE0"));
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(replyKeyboardMarkup);
        }
        return message;

    }


}
