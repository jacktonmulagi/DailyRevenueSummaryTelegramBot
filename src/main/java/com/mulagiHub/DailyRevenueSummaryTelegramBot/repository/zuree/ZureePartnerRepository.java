package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.zuree;





import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.zuree.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZureePartnerRepository extends JpaRepository<Partner, Long> {


}
