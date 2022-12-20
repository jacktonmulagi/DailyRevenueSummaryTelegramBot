package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;

import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.breaktime.BreakTimeRevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BreakTimeRevenueHandler {

    @Autowired
    BreakTimeRevenueRepository breakTimeRevenueRepository;



    public List<SumRevenue> findAllRevenue(String time){
         return breakTimeRevenueRepository.findAllRevenue(time);
    }

}
