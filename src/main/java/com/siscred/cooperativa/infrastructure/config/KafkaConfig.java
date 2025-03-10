package com.siscred.cooperativa.infrastructure.config;

import com.siscred.cooperativa.infrastructure.gateways.kafka.dto.VotoKafkaDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final String bootstrapServersConfig;
    private final String group;
    private final String autoOffsetResetConfig;

    public KafkaConfig(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServersConfig,
            @Value("${spring.kafka.consumer.group-id}") String group,
            @Value("${spring.kafka.consumer.auto-offset-reset}") String autoOffsetResetConfig) {
        this.bootstrapServersConfig = bootstrapServersConfig;
        this.group = group;
        this.autoOffsetResetConfig = autoOffsetResetConfig;
    }

    @Bean
    public ConsumerFactory<String, VotoKafkaDTO> votoConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServersConfig);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.group);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetResetConfig);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VotoKafkaDTO> votoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VotoKafkaDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(votoConsumerFactory());
        return factory;
    }
}
