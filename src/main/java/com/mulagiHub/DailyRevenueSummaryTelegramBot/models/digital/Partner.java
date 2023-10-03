package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.digital;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


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


