package com.mulagiHub.DailyRevenueSummaryTelegramBot.service;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.handlers.*;
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

    @Autowired
    DigitalRevenueHandler digitalRevenueHandler;

    public String summariesRevenue(String database) {
        List<String> summary = new ArrayList<>();
        LocalDate now = LocalDate.now();
        List<SumRevenue> revenues = new ArrayList<>();
        if(Objects.equals(database, "africom")){
            summary.add(0,"Revenue Summary of Africom media limited on " + now.minus(Period.ofDays(1)) + System.lineSeparator() );
             revenues = africomRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        } else if (Objects.equals(database, "breaktime")) {
            summary.add(0,"Revenue Summary of Breaktime  on " + now.minus(Period.ofDays(1)) + System.lineSeparator() );
            revenues = breakTimeRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }
        else if (Objects.equals(database, "tajbuzz")) {
            summary.add(0,"Revenue Summary of Tajbuzz on " + now.minus(Period.ofDays(1)) + System.lineSeparator() );
            revenues = tajBuzzRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }
        else if (Objects.equals(database, "zuree")) {
            summary.add(0,"Revenue Summary of Zuree on " + now.minus(Period.ofDays(1)) + System.lineSeparator() );
            revenues = zureeRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }
        else if (Objects.equals(database, "digital")) {
            summary.add(0,"Revenue Summary of Africom Limited VAS  on " + now.minus(Period.ofDays(1)) + System.lineSeparator() );
            revenues = digitalRevenueHandler.findAllRevenue(String.valueOf(now.minus(Period.ofDays(1))));
        }

        for (SumRevenue revenue : revenues) {
            int position = revenues.indexOf(revenue)+1 ;
            summary.add("No." + position +" "+ revenue.getPartnerName() + " ==>  ksh " + revenue.getAmount() + System.lineSeparator() );

        }
        return summary.toString().replaceAll("\\[|\\]", "");

    }



    public String summariesRevenueFixes(String database) {
        List<String> summary = new ArrayList<>();
        LocalDate now = LocalDate.now();
        List<SumRevenue> revenues = new ArrayList<>();
        if(Objects.equals(database, "africom")){
            summary.add(0,"Revenue Summary of Africom media limited on " + now );
            revenues = africomRevenueHandler.findAllRevenue(String.valueOf(now));
            System.out.println(revenues);
        } else if (Objects.equals(database, "breaktime")) {
            summary.add(0,"Revenue Summary of Breaktime  on " + now);
            revenues = breakTimeRevenueHandler.findAllRevenue(String.valueOf(now));
            System.out.println(revenues);
        }
        else if (Objects.equals(database, "tajbuzz")) {
            summary.add(0,"Revenue Summary of Tajbuzz on " + now);
            revenues = tajBuzzRevenueHandler.findAllRevenue(String.valueOf(now));
            System.out.println(revenues);
        }
        else if (Objects.equals(database, "zuree")) {
            summary.add(0,"Revenue Summary of Zuree on " + now);
            revenues = zureeRevenueHandler.findAllRevenue(String.valueOf(now));
            System.out.println(revenues);
        }else if (Objects.equals(database, "digital")) {
            summary.add(0,"Revenue Summary of Africom Limited VAS  on" + now);
            revenues = digitalRevenueHandler.findAllRevenue(String.valueOf(now));
            System.out.println(revenues);
        }

        for (SumRevenue revenue : revenues) {
            int position = revenues.indexOf(revenue)+1 ;
            summary.add("No." + position +" "+ revenue.getPartnerName() + " ==>  ksh " + revenue.getAmount() );

        }
        return summary.toString().replaceAll("\\[|\\]", "");

    }



}
