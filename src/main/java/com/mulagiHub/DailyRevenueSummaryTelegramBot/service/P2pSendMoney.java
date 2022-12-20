package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;

import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.*;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.CustomerHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.EwalletHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.EwalletTransactionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.SessionHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.EwalletTransaction;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.utils.EwalletTransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mulagiHub.DailyRevenueSummaryTelegramBot.constants.Messages.HomeIcon;


@Service
public class P2pSendMoney {
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    CustomerHandler customerHandler;
    @Autowired
    EwalletTransactionUtil ewalletTransactionUtil;
    @Autowired
    EwalletHandler ewalletHandler;
    @Autowired
    EwalletTransactionHandler ewalletTransactionHandler;

    public SendMessage confirmPayment(Update update) {

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        Session session = sessionHandler.findByChatIdAndNextStageAndStatusAndPreviousStage(update.getMessage().getChat().getId());

        String senderMobileNumber;
        if (update.getMessage().hasContact()) {
            senderMobileNumber = "254" + update.getMessage().getContact().getPhoneNumber().substring(update.getMessage().getContact().getPhoneNumber().length() - 9);

        } else {
            senderMobileNumber = "254" + update.getMessage().getText().substring(update.getMessage().getText().length() - 9);
        }


        if (Boolean.TRUE.equals(customerHandler.existsByMsisdn(senderMobileNumber))) {
            Customer sender = customerHandler.findUserByMsisdn(session.getMsisdn());
            Customer recipient = customerHandler.findUserByMsisdn(senderMobileNumber);
            session.setNextStage(SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN);
            session.setPreviousStage(SessionStage.ENTER_RECIPIENT_MSISDN);
            session.setLastUpdatedBy("app");
            session.setLastUpdatedAt(LocalDateTime.now());
            sessionHandler.saveSession(session);


            EwalletTransaction ewalletTransaction = new EwalletTransaction();
            ewalletTransaction.setCreatedAt(LocalDateTime.now());
            ewalletTransaction.setCreatedBy("app");
            ewalletTransaction.setLastUpdatedAt(LocalDateTime.now());
            ewalletTransaction.setStatus(EwalletTransactionStatus.ACTIVE);
            ewalletTransaction.setStage(TransactionStage.NEW_TRANSACTION);
            ewalletTransaction.setUuid(UUID.randomUUID().toString());
            ewalletTransaction.setUserId(sender.getId());
            ewalletTransaction.setSenderMobile(sender.getMsisdn());
            ewalletTransaction.setSessionUuid(session.getUuid());
            ewalletTransaction.setRecipientMobile(recipient.getMsisdn());
            ewalletTransaction.setTransactionType(TransactionType.P2P);
            ewalletTransaction.setAction(Action.CREDIT);
            ewalletTransaction.setTrxFee((float) 0);
            ewalletTransaction.setSenderCurrentEBalance(ewalletHandler.findEwalletByCustomerId(sender.getId()).getEBalance());
            ewalletTransaction.setRecipientCurrentEBalance(ewalletHandler.findEwalletByCustomerId(recipient.getId()).getEBalance());
            ewalletTransactionHandler.save(ewalletTransaction);
            message.setText("Enter the amount you wish to send");
            keyboardFirstRow.add(new KeyboardButton(HomeIcon));

        } else {
            session.setNextStage(SessionStage.CLOSED);
            session.setPreviousStage(SessionStage.ENTER_RECIPIENT_MSISDN);
            session.setStatus(GeneralStatus.CLOSED);
            message.setText("\uD83D\uDE1E This user is not registered to our service");
            sessionHandler.saveSession(session);
            keyboardFirstRow.add(new KeyboardButton(HomeIcon));
        }


        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;

    }


    public SendMessage enterAmountToSend(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());


        Session session = sessionHandler.findSessionByChatIdAndNextStageEnterAmountAndStatusAndPreviousStageEnterRecipientAmount(update.getMessage().getChat().getId());

        Customer sender = customerHandler.findUserByMsisdn(session.getMsisdn());
        EwalletTransaction updateEwalletTransaction = ewalletTransactionHandler.findEwalletTransactionBySessionUuidAndUserIdAndStatus(session.getUuid(), sender.getId());
        Customer recipient = customerHandler.findUserByMsisdn(updateEwalletTransaction.getRecipientMobile());


        if (Float.parseFloat(update.getMessage().getText()) > ewalletHandler.findEwalletByCustomerId(sender.getId()).getEBalance()) {
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboard = new ArrayList<>();

            KeyboardRow keyboardFirstRow = new KeyboardRow();
            session.setNextStage(SessionStage.CLOSED);
            session.setPreviousStage(SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN);
            session.setStatus(GeneralStatus.CLOSED);
            message.setText("\uD83D\uDE1E You have insufficient balance to send ksh " + Float.parseFloat(update.getMessage().getText()) + ". Your balance is  ksh" + ewalletHandler.findEwalletByCustomerId(sender.getId()).getEBalance());
            sessionHandler.saveSession(session);
            keyboardFirstRow.add(new KeyboardButton(HomeIcon));
            updateEwalletTransaction.setStatus(EwalletTransactionStatus.FAILED);
            updateEwalletTransaction.setStage(TransactionStage.FAILED);
            updateEwalletTransaction.setLastUpdatedAt(LocalDateTime.now());
            updateEwalletTransaction.setLastUpdatedBy("app");
            updateEwalletTransaction.setStatusReason("insufficient funds in the senders account");
            ewalletTransactionHandler.save(updateEwalletTransaction);
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(replyKeyboardMarkup);
        } else {
            session.setNextStage(SessionStage.CONFIRM_RECIPIENT_DETAILS);
            session.setPreviousStage(SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN);
            session.setLastUpdatedBy("app");
            session.setLastUpdatedAt(LocalDateTime.now());
            sessionHandler.saveSession(session);
            updateEwalletTransaction.setAmount(Float.parseFloat(update.getMessage().getText()));
            updateEwalletTransaction.setLastUpdatedAt(LocalDateTime.now());
            updateEwalletTransaction.setLastUpdatedBy("app");
            updateEwalletTransaction.setStage(TransactionStage.AWAITING_APPROVAL);
            ewalletTransactionHandler.save(updateEwalletTransaction);

            message.setText("YOU ARE ABOUT TO SEND KSH " + update.getMessage().getText() + " TO " + recipient.getName() + " CONFIRM WITH YES ");
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            inlineKeyboardButton1.setText("YES");
            inlineKeyboardButton1.setCallbackData("yes_confirm_recipient_p2p_details");
            inlineKeyboardButton2.setText(HomeIcon);
            inlineKeyboardButton2.setCallbackData("no_confirm_recipient_details");
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            keyboardButtonsRow1.add(inlineKeyboardButton1);
            keyboardButtonsRow2.add(inlineKeyboardButton2);
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow1);
            rowList.add(keyboardButtonsRow2);
            inlineKeyboardMarkup.setKeyboard(rowList);
            message.setReplyMarkup(inlineKeyboardMarkup);
        }
        return message;

    }


    public SendMessage enterPin(Update update) {
        System.out.println(update.getCallbackQuery().getMessage().getChatId().toString());
        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        Session session = sessionHandler.findSessionByChatIdAndNextStageConfirmRecipientDetailsAndPreviousStageEnterAmountTosend(update.getCallbackQuery().getMessage().getChat().getId());
        session.setNextStage(SessionStage.ENTER_PIN);
        session.setPreviousStage(SessionStage.CONFIRM_RECIPIENT_DETAILS);
        session.setLastUpdatedBy("app");
        session.setLastUpdatedAt(LocalDateTime.now());
        sessionHandler.saveSession(session);
        message.setText("ENTER YOUR PIN ");
        keyboardFirstRow.add(new KeyboardButton(HomeIcon));
        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;

    }


    public SendMessage confirmSenderPin(Update update) {
        SendMessage messageSender = new SendMessage();
        SendMessage messagerecipient = new SendMessage();
        messageSender.setChatId(update.getMessage().getChatId().toString());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        Session session = sessionHandler.findSessionByChatIdAndNextStageNextEnterPinAndStatusAndPreviousConfirmRecipientDetails(update.getMessage().getChat().getId());
        Customer sender = customerHandler.findUserByMsisdn(session.getMsisdn());
        if (sender.getPin().equals(update.getMessage().getText())) {
            EwalletTransaction updateEwalletTransaction = ewalletTransactionHandler.findEwalletTransactionBySessionUuidAndUserIdAndStatus(session.getUuid(), sender.getId());
            Customer recipient = customerHandler.findUserByMsisdn(updateEwalletTransaction.getRecipientMobile());
            messagerecipient.setChatId(recipient.getTelegramUserId().toString());
            ewalletHandler.debitAccount(updateEwalletTransaction.getAmount(), sender.getId());
            ewalletHandler.creditAccount(updateEwalletTransaction.getAmount(), recipient.getId());
            updateEwalletTransaction.setLastUpdatedAt(LocalDateTime.now());
            updateEwalletTransaction.setLastUpdatedBy("app");
            updateEwalletTransaction.setStatusReason("money was transfer");
            updateEwalletTransaction.setStage(TransactionStage.FINAL);
            ewalletTransactionHandler.save(updateEwalletTransaction);
            Float senderNewewalletBalance = ewalletHandler.findEwalletByCustomerId(sender.getId()).getEBalance();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String now = LocalDateTime.now().format(dtf);
            messageSender.setText("YOUR HAVE SEND KSH " + updateEwalletTransaction.getAmount() + " TO " + recipient.getName().toUpperCase() + " AT " + now + " YOUR NEW EWALLET BALANCE IS  KSH " + senderNewewalletBalance);
        }
        keyboardFirstRow.add(new KeyboardButton("Home \uD83C\uDFE0"));
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        messageSender.setReplyMarkup(replyKeyboardMarkup);
        return messageSender;

    }


    public SendMessage notifyTheRecipient(Update update) {
        SendMessage messageRecipient = new SendMessage();
        Session session = sessionHandler.findSessionByChatIdAndNextStageNextEnterPinAndStatusAndPreviousConfirmRecipientDetails(update.getMessage().getChat().getId());
        Customer sender = customerHandler.findUserByMsisdn(session.getMsisdn());
        EwalletTransaction updateEwalletTransaction = ewalletTransactionHandler.findEwalletTransactionBySessionUuidAndUserIdAndStatus(session.getUuid(), sender.getId());
        Customer recipient = customerHandler.findUserByMsisdn(updateEwalletTransaction.getRecipientMobile());
        messageRecipient.setChatId(recipient.getTelegramUserId().toString());
        updateEwalletTransaction.setStatus(EwalletTransactionStatus.APPROVED);
        ewalletTransactionHandler.save(updateEwalletTransaction);
        session.setNextStage(SessionStage.CLOSED);
        session.setPreviousStage(SessionStage.ENTER_PIN);
        session.setStatus(GeneralStatus.CLOSED);
        session.setLastUpdatedBy("app");
        session.setLastUpdatedAt(LocalDateTime.now());
        sessionHandler.saveSession(session);
        Float recipientNewewalletBalance = ewalletHandler.findEwalletByCustomerId(recipient.getId()).getEBalance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String now = LocalDateTime.now().format(dtf);
        messageRecipient.setText("YOUR HAVE RECEIVE KSH " + updateEwalletTransaction.getAmount() + " FROM " + sender.getName().toUpperCase() + " AT " + now + " YOUR NEW EWALLET BALANCE IS  KSH " + recipientNewewalletBalance);
        return messageRecipient;

    }

}
