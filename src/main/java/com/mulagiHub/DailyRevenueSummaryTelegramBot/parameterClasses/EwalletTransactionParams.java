package com.mulagiHub.DailyRevenueSummaryTelegramBot.parameterClasses;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.Action;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Ewallet;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EwalletTransactionParams {

    Customer sender;
    Customer Recipient;
    Session session;
    Ewallet senderEwallet;
    Ewallet RecipientEwallet;
    TransactionType transactionType;
    float amount;
    float trxFee;
    Action action;

}
