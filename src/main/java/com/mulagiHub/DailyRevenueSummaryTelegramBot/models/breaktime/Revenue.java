package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.breaktime;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.ProductType;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tbl_revenues")
public class Revenue {
    @Id
    long id;
    @Column(name ="trx_uuid")
    String trxUuid;
    @Column(name ="retry_number")
    Integer retryNumber;

    @Column(name ="cp_id")
    Integer cpId;
    @Column(name ="cp_name")
    String cpName;
    @Column(name ="partner_id")
    Integer partnerId;
    @Column(name ="partner_name")
    String partnerName;
    @Column(name ="product_id")
    Integer productId;
    @Column(name ="product_name")
    String productName;
    @Column(name ="subscription_id")
    Integer subscriptionId;
    @Column(name ="product_type")
    @Enumerated(EnumType.STRING)
    ProductType productType;
    String msisdn;
    String operation;
    String currency;
    Float amount;
    @Column(name ="retry_policy_id")
    Integer retryPolicyId;
    @Column(name ="created_on")
    String createdOn;
    @Column(name ="created_by")
    String createdBy;

    @Column(name ="lane")
    String lane;


}


