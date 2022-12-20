package com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main;



import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.GeneralStatus;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.enums.SessionStage;
import com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {


 Boolean existsByChatIdAndNextStageAndStatus(Long chatId, SessionStage sessionStage, GeneralStatus generalStatus);

 Session findByChatIdAndNextStageAndStatusAndPreviousStage(Long chatId, SessionStage nextStage, GeneralStatus generalStatus,SessionStage previousStage);




 Session findSessionByChatIdAndNextStageAndStatusAndPreviousStage(Long chatId, SessionStage nextStage, GeneralStatus generalStatus,SessionStage previousStage);

 Session findByChatIdAndNextStageAndStatus(Long chatId, SessionStage sessionStage, GeneralStatus generalStatus);
 Boolean existsByChatIdAndPreviousStageAndStatus(Long chatId, SessionStage sessionStage, GeneralStatus generalStatus);
 Session findByMsisdn(String msisdn);


 Boolean existsByMsisdn(String msisdn);

}
