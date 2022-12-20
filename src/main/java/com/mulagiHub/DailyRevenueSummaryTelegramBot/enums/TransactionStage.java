package com.mulagiHub.DailyRevenueSummaryTelegramBot.enums;

public enum TransactionStage {
    PHONE_NUMBER,
    FULL_NAME,
    NATIONAL_ID_AND_PIN_PENDING,
    D_O_B,
    PIN,
    NEW_TRANSACTION,
    PROCESSING,
    FINAL,
    AWAITING_CALLBACK,
    AWAITING_APPROVAL,


//****************Notification***************
    NOTIFIED,

    SCHEDULED_FOR_REMINDER,
    REMINDED,
    SUCCESS,
    FAILED;
    //****************Notification***************


}
