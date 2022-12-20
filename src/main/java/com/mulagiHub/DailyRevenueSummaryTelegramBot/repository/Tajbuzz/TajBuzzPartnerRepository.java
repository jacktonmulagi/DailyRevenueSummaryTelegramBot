package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.Tajbuzz;




import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.tajbuz.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TajBuzzPartnerRepository extends JpaRepository<Partner, Long> {


}
