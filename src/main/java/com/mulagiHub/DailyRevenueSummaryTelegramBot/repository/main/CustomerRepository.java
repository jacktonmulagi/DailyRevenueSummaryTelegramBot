package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main;




import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.CustomerStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {



 Customer findByMsisdn(String msisdn);

 List<Customer> findAllByStatusOrderByCreatedAt(CustomerStatus customerStatus);

 Customer findByTelegramUserId(Long telegramUserId);


 Boolean existsByMsisdn(String msisdn);


 Boolean existsByTelegramUserId(Long telegramUserId);

}
