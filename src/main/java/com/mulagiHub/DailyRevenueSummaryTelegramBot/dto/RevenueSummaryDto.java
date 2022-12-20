package com.mulagiHub.DailyRevenueSummaryTelegramBot.dto;

import lombok.Data;

@Data
public class RevenueSummaryDto {
    private String phoneNumber;
    private String partnerId;
    private String partnerName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
