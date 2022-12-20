package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.TelegramTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramTransactionRepository extends JpaRepository<TelegramTransaction, Long> {


//    final String RAW_QUERY = "SELECT * FROM tbl_transaction WHERE msisdn=? order by created_on asc ";
//
//    @Query(value = RAW_QUERY, nativeQuery = true)
//    List<SmsTransaction> getTransactionsByMsisdn(String msisdn);
//
//
//    final String LINK_ID_QUERY = "SELECT * FROM tbl_transaction WHERE link_id=? limit 1";
//
//    @Query(value = LINK_ID_QUERY, nativeQuery = true)
//    SmsTransaction getByLinkId(String linkId);
//
//
//    @Query(value = "SELECT * FROM tbl_transaction WHERE uuid=? ", nativeQuery = true)
//    SmsTransaction getByUUid(String refId);
}
