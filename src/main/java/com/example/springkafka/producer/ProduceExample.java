package com.example.springkafka.producer;

import com.example.springkafka.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProduceExample {
    private static final String TOPIC1 = "test1";
    private static final String TOPIC2 = "test2";

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void produce(Product product) {
        kafkaTemplate.send(TOPIC1, product);
        kafkaTemplate.send(TOPIC2, product);
        log.info("메시지를 Produce 했습니다 {}", product);
    }
}
