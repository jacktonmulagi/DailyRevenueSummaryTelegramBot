package com.mulagiHub.DailyRevenueSummaryTelegramBot.utils;

import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.CustomerStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.PinStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.UserType;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
public class CustomerUtil {
    @Autowired
    GetCustomerName getCustomerName;

    public Customer createNewCustomer(Update update) {
        Customer customer = new Customer();
        customer.setMsisdn("254" +update.getMessage().getContact().getPhoneNumber().substring(update.getMessage().getContact().getPhoneNumber().length() - 9));
        customer.setName(getCustomerName.customerName(update));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setStatus(CustomerStatus.PENDING_APPROVAL);
        customer.setUserType(UserType.CUSTOMER);
        customer.setCreatedBy("app");
        customer.setPinStatus(PinStatus.UNSET);
        customer.setLastUpdatedAt(LocalDateTime.now());
        customer.setLastUpdatedBy("app");
        customer.setTelegramUserId(update.getMessage().getChat().getId());
        return customer;
    }



}
