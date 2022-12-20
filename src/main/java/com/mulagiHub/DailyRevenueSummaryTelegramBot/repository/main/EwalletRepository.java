package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Ewallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EwalletRepository extends JpaRepository<Ewallet, Long> {

    Ewallet findEwalletByCustomerId(Long customerId);

    @Modifying
    @Transactional
    @Query(value = "update tbl_ewallet set e_balance=e_balance-? where customer_id=?", nativeQuery = true)
    void debitAccount(Float amount, Long id);

    @Modifying
    @Transactional
    @Query(value = "update tbl_ewallet set e_balance=e_balance+? where customer_id=?", nativeQuery = true)
    void creditAccount(Float amount, Long id);
}
