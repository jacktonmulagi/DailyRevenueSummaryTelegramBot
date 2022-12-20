package com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "tbl_telegram_transaction")
public class TelegramTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String uuid;
    private String msisdn;
    private String message;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "chat_id")
    private String chatId;
    @Column(name = "message_id")
    private String messageId;
    private String status;
    @Column(name = "created_on")
    String createdOn;
    @Column(name = "created_by")
    String createdBy;
    @Column(name = "last_updated_on")
    String lastUpdatedOn;
    @Column(name = "last_updated_by")
    String lastUpdatedBy;

}
