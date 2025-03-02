package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.TipoOprecaoEnum;
import com.siscred.cooperativa.infrastructure.gateways.kafka.dto.VotoKafkaDTO;
import com.siscred.cooperativa.infrastructure.gateways.kafka.producer.VotoKafkaProducerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoKafkaProducerGatewayTest {

    @Mock
    private KafkaTemplate<String, VotoKafkaDTO> kafkaTemplate;

    private final String topic = "test-topic";

    @InjectMocks
    private VotoKafkaProducerGateway votoKafkaProducerGateway;

    @Captor
    private ArgumentCaptor<VotoKafkaDTO> votoKafkaCaptor;

    @BeforeEach
    void setUp() {
        votoKafkaProducerGateway = new VotoKafkaProducerGateway(kafkaTemplate, topic);
    }

    @Test
    void testSend() {
        // Arrange
        VotoDomain votoDomain = new VotoDomain(); // Mock do VotoDomain
        VotoKafkaDTO expectedMessage = VotoKafkaDTO
                .builder()
                .tipoOprecao(TipoOprecaoEnum.REGISTRA_VOTO)
                .votoDomain(votoDomain)
                .build();
        // Act
        votoKafkaProducerGateway.send(votoDomain);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq(topic), votoKafkaCaptor.capture());
        VotoKafkaDTO capturedMessage = votoKafkaCaptor.getValue();
        
        assertEquals(expectedMessage.getTipoOprecao(), capturedMessage.getTipoOprecao());
        assertEquals(expectedMessage.getVotoDomain(), capturedMessage.getVotoDomain());
    }
}
