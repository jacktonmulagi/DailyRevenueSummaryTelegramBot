package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.TelegramTransaction;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main.TelegramTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TelegramTransactionHandler {
    @Autowired
    TelegramTransactionRepository telegramTransactionRepository;



    public TelegramTransaction save(TelegramTransaction telegramTransaction) {
        return telegramTransactionRepository.save(telegramTransaction);
    }


}
