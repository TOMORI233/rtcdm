package com.zjubiomedit.autotask;

import com.zjubiomedit.service.impl.AutoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@Component
@Slf4j
@RestController
public class AutoTask {

    @Autowired
    AutoServiceImpl autoService;

//    @Async
//    @Scheduled(cron = "*/6 * * * * ?")
//    public void test() {
//        autoService.autoInsertDocUser(autoService.getDocCount());
//        autoService.autoInsertPatUser(autoService.getPatCount());
//        log.info(new Date().toString());
//    }

    @Async
    @Scheduled(cron = "0 0 0 ? * 1")
    public void generateWeeklyAlert() {
        log.info("generateWeeklyAlert");
        autoService.autoGenerateWeeklyAlert();
    }

//    public void generateComplianceRate() {
//        //需要确定医嘱次数
//        autoService.autoGenerateCompliance();
//    }
//
//    public void generateManagePlan() {
//        //详细计划未定
//        autoService.autoGenerateManagePlan();
//    }

    @Async
    @Scheduled(cron = "0 15 0 * * ?")
    public void generateDailyAlert() {
        log.info("generateDailyAlert");
        autoService.autoGenerateDailyAlert();
    }

    @Async
    @Scheduled(cron = "0 30 0 */14 * ?")
    public void generateEvaluationRecord(){
        log.info("generateEvaluation");
        autoService.autoGenerateEvaluation();
    }

}
