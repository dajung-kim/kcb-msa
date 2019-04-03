package com.koreacb.msa.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public ScheduledTask(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String payload) {
        log.debug("Sending kafka template. {} / {}", topic, payload);
        kafkaTemplate.send(topic, payload);
    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        send("test123", "hello: " + new DateTime().toString());
    }

    @KafkaListener(topics = "test123")
    public void receiveTopic(ConsumerRecord consumerRecord) {
        log.debug("Received: {}", consumerRecord.toString());
    }
}
