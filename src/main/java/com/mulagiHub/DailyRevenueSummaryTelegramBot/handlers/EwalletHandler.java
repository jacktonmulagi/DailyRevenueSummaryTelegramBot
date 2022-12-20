package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Ewallet;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main.EwalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EwalletHandler {

    @Autowired
    EwalletRepository ewalletRepository;

     public Ewallet findEwalletByCustomerId(Long customerId){
         return ewalletRepository.findEwalletByCustomerId(customerId);
     }

    public Ewallet save(Ewallet obj) {
        return ewalletRepository.save(obj);
    }

    public void debitAccount(Float amount, Long id) {
        ewalletRepository.debitAccount( amount,  id);
    }
    public void creditAccount(Float amount, Long id) {
        ewalletRepository.creditAccount( amount,  id);
    }
}
