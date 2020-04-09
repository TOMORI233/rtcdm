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

    Integer count = 1;

    @Async
    @Scheduled(cron = "*/6 * * * * ?")
    public void test1() {
        autoService.autoInsertDocUser(count);
        count++;
    }


}
