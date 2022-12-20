package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.CustomerHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.SessionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.TelegramTransactionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.mulagiHub.DailyRevenueSummaryTelegramBot.constants.Messages.senderMobileInstruction;


@Service
public class SendMoney {
    @Autowired
    CustomerHandler customerHandler;
    @Autowired
    TelegramTransactionHandler telegramTransactionHandler;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    SessionUtil sessionUtil;
    public SendMessage sendMoneyPeerToPeer(Update update) {
        Customer customer = customerHandler.findByTelegramUserId(update.getMessage().getChat().getId());

        Session session = sessionUtil.createSendPeerToPeerSession(update.getMessage().getChat().getId(),
                SessionStage.INITIATE_SEND_MONEY,SessionStage.ENTER_RECIPIENT_MSISDN, TransactionType.P2P,customer.getMsisdn());
        sessionHandler.saveSession(session);

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
//                message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText(senderMobileInstruction);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        // Create a list of keyboard rows
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Add buttons to the first keyboard row
        keyboardFirstRow.add(new KeyboardButton("Home \uD83C\uDFE0"));

        keyboard.add(keyboardFirstRow);
//                keyboard.add(keyboardSecondRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }


    public SendMessage acceptsTheDetails(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText("enter your kashyka pin starting with *");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();




        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("cancel"));


        keyboard.add(keyboardSecondRow);
//                keyboard.add(keyboardSecondRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }


    public DeleteMessage deleteMessagePinMessage(Update update) {
//        endpoint to confirm whether the pin is correct
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(update.getMessage().getChatId().toString());
        deleteMessage.setMessageId(update.getMessage().getMessageId());
        return deleteMessage;
    }


    public SendMessage moneySent(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        message.setText("confirm you have send  ksh 1000 to jackton mulagi your new kashyka balance is Ksh 5000");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();




        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Home"));


        keyboard.add(keyboardSecondRow);
//                keyboard.add(keyboardSecondRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }


}
