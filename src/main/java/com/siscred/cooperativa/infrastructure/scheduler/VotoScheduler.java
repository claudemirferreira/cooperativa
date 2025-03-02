package com.siscred.cooperativa.infrastructure.scheduler;

import com.siscred.cooperativa.application.usecases.ContabilizarVotoUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VotoScheduler {

    private final ContabilizarVotoUsecase contabilizarVotoUsecase;

    public VotoScheduler(ContabilizarVotoUsecase contabilizarVotoUsecase) {
        this.contabilizarVotoUsecase = contabilizarVotoUsecase;
    }

    @Scheduled(cron = "${spring.scheduler.cron.vote-task}")
    public void contabilizarVoto() {
        log.info("call scheduleVotoTask ");
        contabilizarVotoUsecase.execute();
    }
}
