package com.mulagiHub.DailyRevenueSummaryTelegramBot.utils;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionUtil {
    Session session = new Session();
    public Session createNewSession(Long id, SessionStage customerRegistrationInitiation, SessionStage customerRegistrationEnterPhoneNumber, TransactionType registration) {
        session.setUuid(UUID.randomUUID().toString());
        session.setChatId(id);
        session.setPreviousStage(customerRegistrationInitiation);
        session.setNextStage(customerRegistrationEnterPhoneNumber);
        session.setTransactionType(registration);
        session.setStatus(GeneralStatus.ACTIVE);
        session.setLastUpdatedAt(LocalDateTime.now());
        session.setLastUpdatedBy("app");
        session.setCreatedAt(LocalDateTime.now());
        session.setCreatedBy("app");
        return session;
    }

    public Session createSendPeerToPeerSession(Long id, SessionStage customerRegistrationInitiation, SessionStage customerRegistrationEnterPhoneNumber, TransactionType registration, String msisdn) {
        session.setUuid(UUID.randomUUID().toString());
        session.setChatId(id);
        session.setMsisdn(msisdn);
        session.setPreviousStage(customerRegistrationInitiation);
        session.setNextStage(customerRegistrationEnterPhoneNumber);
        session.setTransactionType(registration);
        session.setStatus(GeneralStatus.ACTIVE);
        session.setLastUpdatedAt(LocalDateTime.now());
        session.setLastUpdatedBy("app");
        session.setCreatedAt(LocalDateTime.now());
        session.setCreatedBy("app");
        return session;
    }

    public Session createCheckBalanceSession(Long id, String msisdn, SessionStage confirmCheckBalanceInitiation, SessionStage confirmCheckBalanceEnterPin, TransactionType balanceInquiry) {
        session.setUuid(UUID.randomUUID().toString());
        session.setChatId(id);
        session.setMsisdn(msisdn);
        session.setPreviousStage(confirmCheckBalanceInitiation);
        session.setNextStage(confirmCheckBalanceEnterPin);
        session.setTransactionType(balanceInquiry);
        session.setStatus(GeneralStatus.ACTIVE);
        session.setLastUpdatedAt(LocalDateTime.now());
        session.setLastUpdatedBy("app");
        session.setCreatedAt(LocalDateTime.now());
        session.setCreatedBy("app");
        return session;
    }


//    public Session updateSession (){
//        Session session = new Session();
//        session.setChatId();
//        session.setPreviousStage();
//        session.setNextStage();
//        session.setTransactionType();
//        session.setStatus(GeneralStatus.ACTIVE);
//        session.setLastUpdatedAt(LocalDateTime.now());
//        session.setLastUpdatedBy("app");
//        session.setMsisdn(customer.getMsisdn());
//        session.setCreatedAt(LocalDateTime.now());
//        session.setCreatedBy("app");
//    }
}
