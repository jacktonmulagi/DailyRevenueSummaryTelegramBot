package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.EwalletTransactionStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.EwalletTransaction;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main.EwalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EwalletTransactionHandler {
    @Autowired
    EwalletTransactionRepository ewalletTransactionRepository;

    public EwalletTransaction save(EwalletTransaction ewalletTransaction) {
        return ewalletTransactionRepository.save(ewalletTransaction);
    }

    public EwalletTransaction findEwalletTransactionBySessionUuidAndUserIdAndStatus(String uuid, Long userId){
        return ewalletTransactionRepository.findEwalletTransactionBySessionUuidAndUserIdAndStatus(uuid,userId, EwalletTransactionStatus.ACTIVE);
    }
}
