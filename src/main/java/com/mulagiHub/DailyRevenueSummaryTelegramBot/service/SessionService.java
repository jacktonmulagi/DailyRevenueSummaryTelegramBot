package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.CustomerStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.PinStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.CustomerHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.EwalletHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.SessionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Ewallet;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.EwalletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SessionService {
    Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    CustomerHandler customerHandler;
    @Autowired
    SessionHandler sessionHandler;

    @Autowired
    EwalletUtil ewalletUtil;

    @Autowired
    EwalletHandler ewalletHandler;


    public Boolean sessionExist(Long chatId) {
        return sessionHandler.existsBySessionId(chatId);
    }

    public Boolean existsBySessionIdFindByPin(Long chatId) {
        return sessionHandler.existsBySessionIdFindByPin(chatId);
    }

    public Boolean existsBySessionIdFindByConfirmNewPin(Long chatId) {
        return sessionHandler.existsBySessionIdFindByConfirmNewPin(chatId);
    }


    public Boolean findClosedSessionAndStatusDone(Long chatId) {
        return sessionHandler.findClosedSessionAndStatusDone(chatId);
    }

    public  Boolean findActiveRegistrationSessionPreviousCustomerInitiated(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousCustomerInitiated(chatId);
    }

    public Boolean findActiveRegistrationSessionNextCustomerInitiated(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextCustomerInitiated(chatId);
    }


    public  Boolean findActiveRegistrationSessionPreviousCustomerEnterPhoneNumber(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousCustomerEnterPhoneNumber(chatId);
    }

    public Boolean findActiveRegistrationSessionNextCustomerEnterNationalId(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextCustomerEnterNationalId(chatId);
    }

    public  Boolean findActiveRegistrationSessionPreviousCustomerEnterNationalId(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousCustomerEnterNationalId(chatId);
    }

    public Boolean findActiveRegistrationSessionNextCustomerEnterNewPin(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextCustomerEnterNewPin(chatId);
    }


    public  Boolean findActiveRegistrationSessionPreviousCustomerEnterNewPin(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousCustomerEnterNewPin(chatId);
    }

    public Boolean findActiveRegistrationSessionNextCustomerConfirmNewPin(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextCustomerConfirmNewPin(chatId);
    }

    public  Boolean findActiveRegistrationSessionPreviousEnterRecipientMsisdn(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousEnterRecipientMsisdn(chatId);
    }

    public Boolean findActiveRegistrationSessionNextEnterAmountToSendMsisdn(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextEnterAmountToSendMsisdn(chatId);
    }

    public  Boolean findActiveRegistrationSessionPreviousEnterAmountToSendMsisdn(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousEnterAmountToSendMsisdn(chatId);
    }

    public Boolean findActiveRegistrationSessionNextConfirmRecipientDetails(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextConfirmRecipientDetails(chatId);
    }


    public  Boolean findActiveRegistrationSessionPreviousConfirmRecipientDetails(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousConfirmRecipientDetails(chatId);
    }

    public Boolean findActiveRegistrationSessionNextEnterPin(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextEnterPin(chatId);
    }


    public  Boolean findActiveRegistrationSessionPreviousConfirmCheckBalanceInitiation(Long chatId){
        return sessionHandler.findActiveRegistrationSessionPreviousConfirmCheckBalanceInitiation(chatId);
    }

    public Boolean findActiveRegistrationSessionNextConfirmCheckBalanceEnterPin(Long chatId){
        return sessionHandler.findActiveRegistrationSessionNextConfirmCheckBalanceEnterPin(chatId);
    }


    public SendMessage updateSessionWithUserNationalId(Update update) {
        try {
            Session session = sessionHandler.findByChatIdAndNextStageAndStatus(update.getMessage().getChat().getId());

            session.setNextStage(SessionStage.CUSTOMER_REGISTRATION_ENTER_NEW_PIN);
            session.setPreviousStage(SessionStage.NATIONAL_ID);
            session.setLastUpdatedAt(LocalDateTime.now());
            session.setLastUpdatedBy(update.getMessage().getChat().getUserName());
            sessionHandler.saveSession(session);


            Customer customer = customerHandler.findUserByMsisdn(session.getMsisdn());
            customer.setIdNumber(update.getMessage().getText());
            customer.setPinStatus(PinStatus.UNSET);
            customer.setLastUpdatedAt(LocalDateTime.now());
            customer.setLastUpdatedBy("app");
            customerHandler.SaveUser(customer);

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Your Id number was add correctly .Enter your 4 digit number as your pin️");

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboard = new ArrayList<>();

            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("cancel"));

            // Add all of the keyboard rows to the list
            keyboard.add(keyboardFirstRow);
            // and assign this list to our keyboard
            replyKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(replyKeyboardMarkup);
            return message;
        } catch (Exception exception) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(String.valueOf(exception));
            return message;

        }
    }


    public SendMessage updateSessionWithUserPin(Update update) {
        try {
            Session session = sessionHandler.findByChatIdAndNextStageAndStatusSetNewPin(update.getMessage().getChat().getId());
            session.setNextStage(SessionStage.CUSTOMER_REGISTRATION_ENTER_CONFIRM_NEW_PIN);
            session.setPreviousStage(SessionStage.CUSTOMER_REGISTRATION_ENTER_NEW_PIN);
            session.setLastUpdatedAt(LocalDateTime.now());
            session.setLastUpdatedBy(update.getMessage().getChat().getUserName());
            sessionHandler.saveSession(session);


            Customer customer = customerHandler.findUserByMsisdn(session.getMsisdn());
            customer.setPin(update.getMessage().getText());
            customer.setPinStatus(PinStatus.SET);
            customer.setLastUpdatedAt(LocalDateTime.now());
            customer.setLastUpdatedBy("app");
            customerHandler.SaveUser(customer);

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Kindly confirm your  pin  again️");
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboard = new ArrayList<>();

            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("cancel"));

            // Add all of the keyboard rows to the list
            keyboard.add(keyboardFirstRow);
            // and assign this list to our keyboard
            replyKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(replyKeyboardMarkup);
            return message;
        } catch (Exception exception) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(String.valueOf(exception));
            return message;

        }
    }

    public SendMessage updateSessionWithUserConfirmNewPin(Update update) {
        try {
            Session session = sessionHandler.findByChatIdAndNextStageAndStatusSetConfirmPin(update.getMessage().getChat().getId());

            Customer customer = customerHandler.findUserByMsisdn(session.getMsisdn());
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String  now = LocalDateTime.now().format(dtf);

            if(customer.getPin().equals(update.getMessage().getText())){
                session.setStatus(GeneralStatus.CLOSED);
                session.setNextStage(SessionStage.CLOSED);
                session.setPreviousStage(SessionStage.CUSTOMER_REGISTRATION_ENTER_CONFIRM_NEW_PIN);
                session.setLastUpdatedAt(LocalDateTime.now());
                session.setLastUpdatedBy(update.getMessage().getChat().getUserName());
                sessionHandler.saveSession(session);

                customer.setPinStatus(PinStatus.SET);
                customer.setLastUpdatedAt(LocalDateTime.now());
                customer.setLastUpdatedBy("app");
                customer.setStatus(CustomerStatus.ACTIVE);
                customerHandler.SaveUser(customer);

                Ewallet senderEwallet;

                senderEwallet = ewalletUtil.createEwallet(customer);
                ewalletHandler.save(senderEwallet);
                message.setText("Your account was created successful on "+ now + " Your new kashyka balance is " + senderEwallet.getEBalance());
            }else {
                session.setNextStage(SessionStage.CUSTOMER_REGISTRATION_ENTER_NEW_PIN);
                session.setPreviousStage(SessionStage.NATIONAL_ID);
                session.setStatus(GeneralStatus.ACTIVE);
                session.setLastUpdatedAt(LocalDateTime.now());
                session.setLastUpdatedBy(update.getMessage().getChat().getUserName());
                message.setText("⚠️ ! Your PIN DONT MATCH KINDLY SET NEW PIN AGAIN");
                sessionHandler.saveSession(session);


                customer.setPinStatus(PinStatus.UNSET);
                customer.setPin("");
                customer.setLastUpdatedAt(LocalDateTime.now());
                customer.setLastUpdatedBy("app");
                customer.setStatus(CustomerStatus.PENDING_APPROVAL);
                customerHandler.SaveUser(customer);
            }
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboard = new ArrayList<>();

            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("Home \uD83C\uDFE0"));

            // Add all of the keyboard rows to the list
            keyboard.add(keyboardFirstRow);
            // and assign this list to our keyboard
            replyKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(replyKeyboardMarkup);
            return message;
        } catch (Exception exception) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(String.valueOf(exception));
            return message;

        }
    }


    public  Boolean findActiveSessionNextStageEnterRecipientMsisdn(Long chatId){

        return sessionHandler.findActiveSessionNextStageEnterRecipientMsisdn(chatId);
    }

    public  Boolean findActiveSessionPreviousStageInitiateSendMoney(Long chatId){

        return sessionHandler.findActiveSessionPreviousStageInitiateSendMoney(chatId);
    }


}
