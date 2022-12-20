package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tbl_ewallet")
public class Ewallet implements Serializable {

    private String uuid;
    @Id
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "e_balance")
    private Float eBalance;
    @Column(name = "locked_ewallet_balance_amount")
    private Float lockedEwalletBalanceAmount;
    @Column(name = "ewallet_balance_stability")
    private Integer ewalletBalanceStability;
    @Enumerated(value = EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "status_reason")
    private String statusReason;
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



