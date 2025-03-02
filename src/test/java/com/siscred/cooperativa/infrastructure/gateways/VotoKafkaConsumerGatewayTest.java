package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.usecases.RegistrarVotoUsecase;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.gateways.kafka.consumer.VotoKafkaConsumerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VotoKafkaConsumerGatewayTest {

    @Mock
    private RegistrarVotoUsecase registrarVotoUsecase;  // Mock do caso de uso

    @InjectMocks
    private VotoKafkaConsumerGateway votoKafkaConsumerGateway;  // Injeção da classe que estamos testando

    @BeforeEach
    void setUp() {
        // Configuração do ambiente de teste, caso necessário
    }

    @Test
    void testListen() {
        // Criação de um exemplo de VotoDomain
        VotoDomain votoDomain = new VotoDomain();
        votoDomain.setCpf("12345678900");

        // Chama o método listen diretamente
        votoKafkaConsumerGateway.listen(votoDomain);

        // Verifica se o método execute do registrarVotoUsecase foi chamado com o VotoDomain
        verify(registrarVotoUsecase, times(1)).execute(votoDomain);
    }
}
