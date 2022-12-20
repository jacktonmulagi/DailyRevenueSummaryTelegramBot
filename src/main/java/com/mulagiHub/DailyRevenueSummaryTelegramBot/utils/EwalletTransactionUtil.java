package com.mulagiHub.DailyRevenueSummaryTelegramBot.utils;




import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.EwalletTransactionStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.EwalletTransaction;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.parameterClasses.EwalletTransactionParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EwalletTransactionUtil {


    public EwalletTransaction createEwalletTrx(EwalletTransactionParams params) {

        EwalletTransaction ewalletTransaction = new EwalletTransaction();

        ewalletTransaction.setUuid(UUID.randomUUID().toString());
        ewalletTransaction.setSessionUuid(params.getSession().getUuid());
        ewalletTransaction.setUserId(params.getSender().getId());
        ewalletTransaction.setSenderMobile(params.getSender().getMsisdn());
        ewalletTransaction.setRecipientMobile(params.getRecipient().getMsisdn());
        ewalletTransaction.setTransactionType(params.getTransactionType());
        ewalletTransaction.setAction(params.getAction());
        ewalletTransaction.setAmount(params.getAmount());
        ewalletTransaction.setTrxFee(params.getTrxFee());
        ewalletTransaction.setSenderCurrentEBalance(params.getSenderEwallet().getEBalance());
        ewalletTransaction.setRecipientCurrentEBalance(params.getRecipientEwallet().getEBalance());
        ewalletTransaction.setStatus(EwalletTransactionStatus.ACTIVE);
        ewalletTransaction.setStage(TransactionStage.NEW_TRANSACTION);
        ewalletTransaction.setCreatedAt(LocalDateTime.now());
        ewalletTransaction.setLastUpdatedAt(LocalDateTime.now());

        return ewalletTransaction;
    }


}
