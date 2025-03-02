package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.gateways.kafka.producer.VotoKafkaProducerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"kafka.topic=test-topic"})  // Definindo o valor do tópico diretamente no teste
class VotoKafkaProducerGatewayTest {

    @Mock(lenient = true)
    private KafkaTemplate<String, VotoDomain> kafkaTemplate;

    @InjectMocks
    private VotoKafkaProducerGateway votoKafkaProducerGateway;

    @Value("${kafka.topic}")
    private String topic;  // O valor do tópico será injetado aqui

    @BeforeEach
    void setUp() {
        // Aqui você pode verificar se o tópico não é null
        System.out.println("Topic: " + topic);  // Isso deve imprimir "test-topic"
    }

    @Test
    void testSend() {
        VotoDomain votoDomain = new VotoDomain();
        votoDomain.setCpf("12345678900");

        // Simula o comportamento do mock para evitar NullPointerException
        when(kafkaTemplate.send(eq(topic), any(VotoDomain.class))).thenReturn(null);

        // Chama o método que será testado
        votoKafkaProducerGateway.send(votoDomain);

        // Verifica se o método send foi chamado no KafkaTemplate com o tópico correto
        ArgumentCaptor<VotoDomain> votoCaptor = ArgumentCaptor.forClass(VotoDomain.class);
        verify(kafkaTemplate, times(1)).send(eq(topic), votoCaptor.capture());

        // Verifique se o objeto VotoDomain capturado é o esperado
        assert votoCaptor.getValue().getCpf().equals("12345678900");
    }
}
