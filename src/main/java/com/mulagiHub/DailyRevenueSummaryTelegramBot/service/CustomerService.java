package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.CustomerHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.SessionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.CustomerUtil;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    CustomerHandler customerHandler;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    CustomerUtil customerUtil;


    public List<Customer> findAllByStatusOrderByCreatedAt(){
        return customerHandler.findAllByStatusOrderByCreatedAt();
    };
    public Boolean userExist(String msisdn) {
        return customerHandler.existsByMsisdn(msisdn);
    }



    public  Boolean existsByTelegramUserId(Long telegramUserId){
        return customerHandler.existsByTelegramUserId(telegramUserId);
    }



    public SendMessage saveNewUserRegistration(Update update){
        try {
            Customer customer = customerUtil.createNewCustomer(update);
            System.out.println(customer);
            customerHandler.SaveUser(customer);
//            updating the session
            Session session = sessionHandler.findSessionByChatIdAndNextStageAndStatusAndPreviousStageAndMsisdnIsNull(update.getMessage().getChat().getId());
            session.setMsisdn(update.getMessage().getContact().getPhoneNumber());
            session.setNextStage(SessionStage.NATIONAL_ID);
            session.setPreviousStage(SessionStage.CUSTOMER_REGISTRATION_ENTER_PHONE_NUMBER);
            session.setLastUpdatedAt(LocalDateTime.now());
            session.setLastUpdatedBy(update.getMessage().getChat().getUserName());
            sessionHandler.saveSession(session);

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Enter your Id number️");

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
            return  message;
        }catch (Exception exception){
            System.out.println(exception);
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("something went wrong");
            return  message;

        }
    }


}
