package com.mulagiHub.DailyRevenueSummaryTelegramBot;

import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.AfricomRevenueHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledMessageBot extends TelegramLongPollingBot {
    private final String BOT_TOKEN;
    private final String BOT_USERNAME;

    @Autowired
    SendMoney sendMoney;
    @Autowired
    P2pSendMoney p2pSendMoney;
    @Autowired
    Registration registration;
    @Autowired
    CustomerService userService;
    @Autowired
    SessionService sessionService;
    @Autowired
    private Home home;
    @Autowired
    CustomerAccount customerAccount;
    @Autowired
    AfricomRevenueHandler africomRevenueHandler;

    public ScheduledMessageBot(@Value("${bot.BOT_TOKEN}")String botToken,  @Value("${bot.BOT_USERNAME}")String botUsername) {
        BOT_TOKEN = botToken;
        BOT_USERNAME = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage());

//        System.out.println(update.getMessage().getText());
        String regex = "[0-9]+";
        if (update.hasMessage() && update.getMessage() != null && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("Home \uD83C\uDFE0") || update.getMessage().getText().equals("cancel")) {
                try {
                    execute(home.registerOrDefault(update));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().hasText() && update.getMessage().getText().equals("Send money \uD83D\uDCB0")) {
                try {
                    execute(sendMoney.sendMoneyPeerToPeer(update));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().hasText() && update.getMessage().getText().equals("Register  \uD83C\uDD95")) {
                if (Boolean.TRUE.equals(userService.existsByTelegramUserId(update.getMessage().getChat().getId()))) {
                    try {
                        execute(registration.userExists(update));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        execute(registration.newUser(update));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else if (update.getMessage().hasText() && update.getMessage().getText().equals("Account \uD83C\uDFE0")) {
                try {
                    execute(customerAccount.checkBalance(update));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().matches(regex)) {

                if (Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionPreviousCustomerEnterPhoneNumber(update.getMessage().getChat().getId())) && Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionNextCustomerEnterNationalId(update.getMessage().getChat().getId()))) {
                    try {

                        execute(sendMoney.deleteMessagePinMessage(update));
                        execute(sessionService.updateSessionWithUserNationalId(update));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Boolean.TRUE.equals(Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionPreviousCustomerEnterNationalId(update.getMessage().getChat().getId())) && sessionService.findActiveRegistrationSessionNextCustomerEnterNewPin(update.getMessage().getChat().getId())) && update.getMessage().getText().length() == 4) {
                    System.out.println("am here new pin");
//                    new user pin
                    try {
                        execute(sendMoney.deleteMessagePinMessage(update));
                        execute(sessionService.updateSessionWithUserPin(update));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionPreviousCustomerEnterNewPin(update.getMessage().getChat().getId())) && Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionNextCustomerConfirmNewPin(update.getMessage().getChat().getId())) && update.getMessage().getText().length() == 4) {
//                    new user confirm pin
                    try {
                        execute(sendMoney.deleteMessagePinMessage(update));
                        execute(sessionService.updateSessionWithUserConfirmNewPin(update));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionPreviousEnterRecipientMsisdn(update.getMessage().getChat().getId())) && Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionNextEnterAmountToSendMsisdn(update.getMessage().getChat().getId()))) {
//
                    try {
                        execute(p2pSendMoney.enterAmountToSend(update));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionPreviousConfirmRecipientDetails(update.getMessage().getChat().getId())) && Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionNextEnterPin(update.getMessage().getChat().getId()))) {
                    try {
                        execute(sendMoney.deleteMessagePinMessage(update));
                        execute(p2pSendMoney.confirmSenderPin(update));
                        execute(p2pSendMoney.notifyTheRecipient(update));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionPreviousConfirmCheckBalanceInitiation(update.getMessage().getChat().getId()))
                        && Boolean.TRUE.equals(sessionService.findActiveRegistrationSessionNextConfirmCheckBalanceEnterPin(update.getMessage().getChat().getId()))) {

                    try {
                        execute(sendMoney.deleteMessagePinMessage(update));
                        execute(customerAccount.checkBalanceEnterPin(update));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (sessionService.findActiveSessionNextStageEnterRecipientMsisdn(update.getMessage().getChat().getId()) && sessionService.findActiveSessionPreviousStageInitiateSendMoney(update.getMessage().getChat().getId())
                        && update.getMessage().getText().length() == 10
                ) {

                    try {
                        execute(p2pSendMoney.confirmPayment(update));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } else if (update.getMessage().getText().equals("Yes")) {

                try {
                    execute(sendMoney.acceptsTheDetails(update));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().getText().equals("Send money") || update.getMessage().getText().contains("*")) {

                try {
                    execute(sendMoney.deleteMessagePinMessage(update));
                    execute(sendMoney.moneySent(update));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


//


        } else if (update.hasMessage() && update.getMessage() != null && update.getMessage().hasContact()) {

            try {
                if (sessionService.findActiveSessionNextStageEnterRecipientMsisdn(update.getMessage().getChat().getId()) && sessionService.findActiveSessionPreviousStageInitiateSendMoney(update.getMessage().getChat().getId())) {

                    execute(p2pSendMoney.confirmPayment(update));

                } else if (sessionService.findActiveRegistrationSessionPreviousCustomerInitiated(update.getMessage().getChat().getId()) && sessionService.findActiveRegistrationSessionNextCustomerInitiated(update.getMessage().getChat().getId())) {

                    execute(userService.saveNewUserRegistration(update));
                }
//                if (Boolean.TRUE.equals(userService.userExist(update.getMessage().getContact().getPhoneNumber()))){
//                    execute(enterAmount.confirmPayment(update));
//                }else {

//
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("yes_confirm_recipient_p2p_details")

        ) {
            try {
                execute(p2pSendMoney.enterPin(update));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        // return the API token obtained from the Bot Father
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        // return the bot's username
        return BOT_USERNAME;
    }

    @Scheduled(cron = "0 10 0 * * *")
    public void sendAfricomRevenue() {
        List<String> summary = new ArrayList<>();
        LocalDate now = LocalDate.now();
        List<SumRevenue> revenues = africomRevenueHandler.findAllRevenue(String.valueOf(now));
        summary.add(0,"Revenue Summary of Africom media limited on " + now.minus(Period.ofDays(1)) +" is as follows ");
        for (SumRevenue revenue : revenues) {
            int position = revenues.indexOf(revenue)+1 ;
            summary.add("No." + position +" "+ revenue.getPartnerName() + " ==>  ksh " + revenue.getAmount() );

        }
        List<Customer>  customer = userService.findAllByStatusOrderByCreatedAt();
        for (Customer value : customer) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(value.getTelegramUserId()));
            message.setText(summary.toString().replaceAll("\\[|\\]", ""));
            try {
                execute(message);
            } catch (TelegramApiException e) {
                // log the
            }
        }

    }
}
