package com.mulagiHub.DailyRevenueSummaryTelegramBot.enums;

public enum SessionStage {
    //********************** pin stages**************************
    PIN,
    CONFIFM_PIN,

    NATIONAL_ID,
    INITIATE_SEND_MONEY,
    ENTER_RECIPIENT_MSISDN,
    ENTER_AMOUNT_TO_SEND_MSISDN,
    CONFIRM_RECIPIENT_DETAILS,
    CUSTOMER_REGISTRATION_INITIATION,
    CUSTOMER_REGISTRATION_ENTER_PHONE_NUMBER,
    CUSTOMER_REGISTRATION_ENTER_ID_NUMBER,
    CUSTOMER_REGISTRATION_ENTER_NEW_PIN,
    CUSTOMER_REGISTRATION_ENTER_CONFIRM_NEW_PIN,

    CONFIRM_CHECK_BALANCE_INITIATION,
    CONFIRM_CHECK_BALANCE_ENTER_PIN,



    //********************** p2p stages && bal **************************
    NEW,
    CONFIFM_TRX,
    ENTER_PIN,
    CLOSED,

    //********************** withdraw stages **************************

    //********************** paybill stages **************************

    //********************** deposit stages **************************


}
