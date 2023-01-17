package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.AfricomRevenueHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.BreakTimeRevenueHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.TajBuzzRevenueHandler;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.ZureeRevenueHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RevenueSummary {

    @Autowired
    TajBuzzRevenueHandler tajBuzzRevenueHandler;

    @Autowired
    BreakTimeRevenueHandler breakTimeRevenueHandler;

    @Autowired
    AfricomRevenueHandler africomRevenueHandler;
    @Autowired
    ZureeRevenueHandler zureeRevenueHandler;

    public String summariesRevenue(String database) {
        List<String> summary = new ArrayList<>();
        LocalDate now = LocalDate.now();
        List<SumRevenue> revenues = new ArrayList<>();
        if(Objects.equals(database, "africom")){
            summary.add(0,"Revenue Summary of Africom media limited on " + now.minus(Period.ofDays(1)) );
             revenues = africomRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        } else if (Objects.equals(database, "breaktime")) {
            summary.add(0,"Revenue Summary of Breaktime  on " + now.minus(Period.ofDays(1)));
            revenues = breakTimeRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }
        else if (Objects.equals(database, "tajbuzz")) {
            summary.add(0,"Revenue Summary of Tajbuzz on " + now.minus(Period.ofDays(1)));
            revenues = tajBuzzRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }
        else if (Objects.equals(database, "zuree")) {
            summary.add(0,"Revenue Summary of Zuree on " + now.minus(Period.ofDays(1)));
            revenues = zureeRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }

        for (SumRevenue revenue : revenues) {
            int position = revenues.indexOf(revenue)+1 ;
            summary.add("No." + position +" "+ revenue.getPartnerName() + " ==>  ksh " + revenue.getAmount() );

        }
        return summary.toString().replaceAll("\\[|\\]", "");

    }



}