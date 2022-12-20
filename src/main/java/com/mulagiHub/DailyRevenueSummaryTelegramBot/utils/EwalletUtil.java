package com.mulagiHub.DailyRevenueSummaryTelegramBot.utils;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Ewallet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EwalletUtil {


    public Ewallet createEwallet(Customer customer) {
        Ewallet ewallet = new Ewallet();

        ewallet.setUuid(UUID.randomUUID().toString());
        ewallet.setCustomerId(customer.getId());
        ewallet.setEBalance(1000f);
        ewallet.setLockedEwalletBalanceAmount(0f);
        ewallet.setEwalletBalanceStability(1);
        ewallet.setStatus(GeneralStatus.ACTIVE);
        ewallet.setCreatedAt(LocalDateTime.now());
        ewallet.setLastUpdatedAt(LocalDateTime.now());
        return ewallet;
    }
}
