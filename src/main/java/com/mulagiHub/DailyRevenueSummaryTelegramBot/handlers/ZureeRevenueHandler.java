package com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.Tajbuzz.TajBuzzRevenueRepository;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.zuree.ZureeRevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ZureeRevenueHandler {

  @Autowired
    ZureeRevenueRepository zureeRevenueRepository;



    public List<SumRevenue> findAllRevenue(String time){
         return zureeRevenueRepository.findAllRevenue(time);
    }

}
