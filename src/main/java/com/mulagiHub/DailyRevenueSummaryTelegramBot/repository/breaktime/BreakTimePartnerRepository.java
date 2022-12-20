package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.breaktime;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.breaktime.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BreakTimePartnerRepository extends JpaRepository<Partner, Long> {


}
