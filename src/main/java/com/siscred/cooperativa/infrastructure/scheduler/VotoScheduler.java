package com.siscred.cooperativa.infrastructure.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VotoScheduler {

    @Scheduled(cron = "${spring.scheduler.cron.vote-task}")
    public void scheduleVotoTask() {
        log.info("call scheduleVotoTask ");
    }
}
