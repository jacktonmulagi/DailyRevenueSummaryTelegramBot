package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main;




import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tbl_session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private  String uuid;
    @Column(name = "chat_id")
    private Long chatId;
    private String msisdn;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    @Enumerated(value = EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "next_stage")
    @Enumerated(value = EnumType.STRING)
    private SessionStage nextStage;
    @Column(name = "previous_stage")
    @Enumerated(value = EnumType.STRING)
    private SessionStage previousStage;
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
