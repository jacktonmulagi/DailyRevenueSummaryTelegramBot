package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;

import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.Tajbuzz.TajBuzzRevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TajBuzzRevenueHandler {

  @Autowired
    TajBuzzRevenueRepository tajBuzzRevenueRepository;



    public List<SumRevenue> findAllRevenue(String time){
         return tajBuzzRevenueRepository.findAllRevenue(time);
    }

}
