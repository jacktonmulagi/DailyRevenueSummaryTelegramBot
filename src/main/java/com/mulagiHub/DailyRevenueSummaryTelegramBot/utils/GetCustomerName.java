package com.mulagiHub.DailyRevenueSummaryTelegramBot.utils;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class GetCustomerName {
    public String customerName(Update update){
        String customerName = "";
        if(update.getMessage().getChat().getUserName() != null){
            customerName = update.getMessage().getChat().getUserName() ;
        }else if(update.getMessage().getChat().getFirstName() !=null){
            customerName = update.getMessage().getChat().getFirstName() ;
        }else if(update.getMessage().getChat().getLastName() !=null){
            customerName = update.getMessage().getChat().getLastName() ;
        }
        return customerName;
    }
}
