package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.africom.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AfricomRevenueHandler {

    @Autowired
    RevenueRepository revenueRepository;



    public List<SumRevenue> findAllRevenue(String time){
         return revenueRepository.findAllRevenue(time);
    }

}
