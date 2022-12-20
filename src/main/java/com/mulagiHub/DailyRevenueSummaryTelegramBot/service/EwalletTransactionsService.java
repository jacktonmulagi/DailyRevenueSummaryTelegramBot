package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.EwalletTransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EwalletTransactionsService {
    @Autowired
    EwalletTransactionHandler ewalletTransactionHandler;

}
