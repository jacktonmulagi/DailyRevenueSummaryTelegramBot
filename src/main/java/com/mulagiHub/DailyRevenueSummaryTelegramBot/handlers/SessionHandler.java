package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionHandler {
    @Autowired
    SessionRepository sessionRepository;


    public  void saveSession(Session session){
        sessionRepository.save(session);
    }

    public  Boolean existsBySessionId(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId, SessionStage.NATIONAL_ID, GeneralStatus.PENDING);
    }


    public  Boolean existsBySessionIdFindByPin(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId, SessionStage.PIN, GeneralStatus.PENDING);
    }

    public  Boolean existsBySessionIdFindByConfirmNewPin(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId, SessionStage.CONFIFM_PIN, GeneralStatus.PENDING);
    }

    public  Session findByChatIdAndNextStageAndStatus(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatus(chatId,SessionStage.NATIONAL_ID, GeneralStatus.ACTIVE);
    }

    public  Session findByChatIdAndNextStageAndStatusSetNewPin(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_NEW_PIN, GeneralStatus.ACTIVE);
    }

    public  Session findByChatIdAndNextStageAndStatusSetConfirmPin(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_CONFIRM_NEW_PIN, GeneralStatus.ACTIVE);
    }



    public  Boolean findClosedSessionAndStatusDone(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.CLOSED, GeneralStatus.DONE);
    }


    public  Boolean findActiveSessionNextStageEnterRecipientMsisdn(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.ENTER_RECIPIENT_MSISDN, GeneralStatus.ACTIVE);
    }

    public  Boolean findActiveSessionPreviousStageInitiateSendMoney(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.INITIATE_SEND_MONEY, GeneralStatus.ACTIVE);
    }

   public Session findByChatIdAndNextStageAndStatusAndPreviousStage(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatusAndPreviousStage(chatId,SessionStage.ENTER_RECIPIENT_MSISDN,GeneralStatus.ACTIVE,SessionStage.INITIATE_SEND_MONEY);
   }



    public  Boolean findActiveRegistrationSessionPreviousCustomerInitiated(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_INITIATION, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextCustomerInitiated(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_PHONE_NUMBER,GeneralStatus.ACTIVE);
    }


    public  Boolean findActiveRegistrationSessionPreviousCustomerEnterPhoneNumber(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_PHONE_NUMBER, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextCustomerEnterNationalId(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.NATIONAL_ID,GeneralStatus.ACTIVE);
    }


    public  Boolean findActiveRegistrationSessionPreviousCustomerEnterNationalId(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.NATIONAL_ID, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextCustomerEnterNewPin(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_NEW_PIN,GeneralStatus.ACTIVE);
    }

    public  Boolean findActiveRegistrationSessionPreviousCustomerEnterNewPin(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_NEW_PIN, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextCustomerConfirmNewPin(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.CUSTOMER_REGISTRATION_ENTER_CONFIRM_NEW_PIN,GeneralStatus.ACTIVE);
    }


    public  Boolean findActiveRegistrationSessionPreviousEnterRecipientMsisdn(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.ENTER_RECIPIENT_MSISDN, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextEnterAmountToSendMsisdn(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN,GeneralStatus.ACTIVE);
    }


    public Session findSessionByChatIdAndNextStageEnterAmountAndStatusAndPreviousStageEnterRecipientAmount(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatusAndPreviousStage(chatId,SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN,GeneralStatus.ACTIVE,SessionStage.ENTER_RECIPIENT_MSISDN);
    }


    public  Boolean findActiveRegistrationSessionPreviousEnterAmountToSendMsisdn(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextConfirmRecipientDetails(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.CONFIRM_RECIPIENT_DETAILS,GeneralStatus.ACTIVE);
    }

    public Session findSessionByChatIdAndNextStageConfirmRecipientDetailsAndPreviousStageEnterAmountTosend(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatusAndPreviousStage(chatId,SessionStage.CONFIRM_RECIPIENT_DETAILS,GeneralStatus.ACTIVE,SessionStage.ENTER_AMOUNT_TO_SEND_MSISDN);
    }



    public  Boolean findActiveRegistrationSessionPreviousConfirmRecipientDetails(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.CONFIRM_RECIPIENT_DETAILS, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextEnterPin(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.ENTER_PIN,GeneralStatus.ACTIVE);
    }

    public Session findSessionByChatIdAndNextStageNextEnterPinAndStatusAndPreviousConfirmRecipientDetails(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatusAndPreviousStage(chatId,
                SessionStage.ENTER_PIN, GeneralStatus.ACTIVE,
                SessionStage.CONFIRM_RECIPIENT_DETAILS);
    }

    public  Boolean findActiveRegistrationSessionPreviousConfirmCheckBalanceInitiation(Long chatId){
        return sessionRepository.existsByChatIdAndPreviousStageAndStatus(chatId,SessionStage.CONFIRM_CHECK_BALANCE_INITIATION, GeneralStatus.ACTIVE);
    }

    public Boolean findActiveRegistrationSessionNextConfirmCheckBalanceEnterPin(Long chatId){
        return sessionRepository.existsByChatIdAndNextStageAndStatus(chatId,SessionStage.CONFIRM_CHECK_BALANCE_ENTER_PIN,GeneralStatus.ACTIVE);
    }

   public Session findSessionByChatIdAndNextStageAndStatusAndPreviousStageAndMsisdnIsNull(Long chatId){
        return sessionRepository.findSessionByChatIdAndNextStageAndStatusAndPreviousStage(chatId,
                SessionStage.CUSTOMER_REGISTRATION_ENTER_PHONE_NUMBER, GeneralStatus.ACTIVE,
                SessionStage.CUSTOMER_REGISTRATION_INITIATION);
   }


    public Session findSessionByChatIdAndNextConfirmCheckBalanceEnterPinAndPreviousConfirmCheckBalanceInitiation(Long chatId){
        return sessionRepository.findByChatIdAndNextStageAndStatusAndPreviousStage(chatId,
                SessionStage.CONFIRM_CHECK_BALANCE_ENTER_PIN, GeneralStatus.ACTIVE,
                SessionStage.CONFIRM_CHECK_BALANCE_INITIATION);
    }
}
