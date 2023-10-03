package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;

import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.digital.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DigitalRevenueHandler {

    @Autowired
    RevenueRepository revenueRepository;



    public List<SumRevenue> findAllRevenue(String time){
         return revenueRepository.findAllRevenue(time);
    }

}
