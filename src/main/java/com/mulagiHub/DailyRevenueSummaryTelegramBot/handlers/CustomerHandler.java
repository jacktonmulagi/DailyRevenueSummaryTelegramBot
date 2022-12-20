package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;




import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.CustomerStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerHandler {
    @Autowired
    CustomerRepository customerRepository;

    public Customer findUserByMsisdn(String msisdn) {
        return customerRepository.findByMsisdn(msisdn);
    }



    public Customer findByTelegramUserId(Long telegramUserId){
        return customerRepository.findByTelegramUserId(telegramUserId);
    }


    public List<Customer> findAllByStatusOrderByCreatedAt(){
        return customerRepository.findAllByStatusOrderByCreatedAt(CustomerStatus.ACTIVE);
    };


    public  Boolean existsByMsisdn(String msisdn){
        return customerRepository.existsByMsisdn(msisdn);
    }

    public  Boolean existsByTelegramUserId(Long telegramUserId){
        return customerRepository.existsByTelegramUserId(telegramUserId);
    }

    public  void SaveUser(Customer customer){
        customerRepository.save(customer);
    }
}
