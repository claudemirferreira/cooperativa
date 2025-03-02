package com.siscred.cooperativa.infrastructure.scheduler;

import com.siscred.cooperativa.application.usecases.ContabilizarVotoUsecase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VotoSchedulerTest {

    @Mock
    private ContabilizarVotoUsecase contabilizarVotoUsecase;

    @InjectMocks
    private VotoScheduler votoScheduler;

    @Test
    void testContabilizarVoto() {
        votoScheduler.contabilizarVoto();

        verify(contabilizarVotoUsecase, times(1)).execute();  // Verifica se o método execute() foi chamado uma vez
    }
}
