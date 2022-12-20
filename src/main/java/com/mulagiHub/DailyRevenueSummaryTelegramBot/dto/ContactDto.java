package com.mulagiHub.DailyRevenueSummaryTelegramBot.dto;

import lombok.Data;
@Data
public class ContactDto {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String userID;
    private String vCard;
}
