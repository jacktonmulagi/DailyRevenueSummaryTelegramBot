package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.digital;


import com.mulagiHub.DailyRevenueSummaryTelegramBot.dto.SumRevenue;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.digital.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    String FIND_REVENUE_TODAY = "select sum(a.amount) as amount, a.partner_id as partnerId ,b.name as partnerName from zusubportal.tbl_revenues a ,zusubportal.tbl_partners b where date(a.created_on)=? and a.partner_id = b.id group by a.partner_id order by a.partner_id asc limit 10;";
    @Query(value = FIND_REVENUE_TODAY, nativeQuery = true)
    List<SumRevenue> findAllRevenue(String time);


}
