package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.CustomerStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.PinStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tbl_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Column(unique = true)
    private String msisdn;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
    @Column(name = "id_number")
    private String idNumber;
    @Column(name = "telegram_user_id")
    private Long telegramUserId;
    @Column(name = "pin_status")
    private PinStatus pinStatus;
    private String pin;
    @Enumerated(value = EnumType.STRING)
    private CustomerStatus status;
    @Basic
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Basic
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;


}


