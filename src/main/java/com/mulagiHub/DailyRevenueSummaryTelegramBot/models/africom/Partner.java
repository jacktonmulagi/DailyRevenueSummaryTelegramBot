package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.africom;



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
@Entity(name = "tbl_partners")
public class Partner {

    @Id
    long id;
    @Column(name ="name")
    String name;
    @Column(name ="additional_data")
    String additionalData;

    @Column(name ="uuid")
    String uuid;
    @Column(name ="status")
    String status;
    @Column(name ="callback_policy")
    String callbackPolicy;
    @Column(name ="last_updated_on")
    String lastUpdatedOn;
    @Column(name ="last_updated_by")
    String lastUpdatedBy;
    @Column(name ="created_on")
    String createdOn;
    @Column(name ="created_by")
    String createdBy;



}


