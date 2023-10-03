package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.digital;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.digital.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DigitalPartnerRepository extends JpaRepository<Partner, Long> {


}
