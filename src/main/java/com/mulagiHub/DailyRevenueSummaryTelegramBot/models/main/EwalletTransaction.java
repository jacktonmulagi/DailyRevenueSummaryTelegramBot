package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main;




import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.Action;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.EwalletTransactionStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tbl_ewallet_transaction")
public class EwalletTransaction implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String uuid;
    @Column(name = "session_uuid")
    private String sessionUuid;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "sender_mobile")
    private String senderMobile;
    @Column(name = "recipient_mobile")
    private String recipientMobile;
    @Enumerated(value = EnumType.STRING)
    private Action action;
    private Float amount;
    @Column(name = "trx_fee")
    private Float trxFee;
    @Column(name = "sender_previous_ebalance")
    private Float senderPreviousEBalance;
    @Column(name = "sender_current_ebalance")
    private Float senderCurrentEBalance;
    @Column(name = "recipient_previous_ebalance")
    private Float recipientPreviousEBalance;
    @Column(name = "recipient_current_ebalance")
    private Float recipientCurrentEBalance;

    @Enumerated(value = EnumType.STRING)
    private EwalletTransactionStatus status;
    @Column(name = "status_reason")
    private String statusReason;
    @Enumerated(value = EnumType.STRING)
    private TransactionStage stage;
    @Column(name = "stage_reason")
    private String stageReason;
    @Column(name = "notification_status")
    private String notificationStatus;
    @Basic
    @Column(name = "notification_time")
    private LocalDateTime notificationTime;
    @Column(name = "notification_id")
    private String notificationId;
    @Column(name = "created_at")
    @Basic
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Basic
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
}
