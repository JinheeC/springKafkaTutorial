package com.example.springkafka.consumer;

import com.example.springkafka.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumeExample {
    private static final String TOPIC1 = "test1";
    private static final String TOPIC2 = "test2";

    @KafkaListener(topics = TOPIC1, groupId = "example")
    public void listen(Product product) {
        log.info("listen: 메시지를 받았습니다. {}", product);
    }

    @KafkaListener(topics = TOPIC1, groupId = "forRetryExample")
    public void listenAndMakeException(Product product) {
        log.error("Retry 예제용 예외가 발생했습니다.");
        throw new RuntimeException("Retry 예제용 예외입니다.");
    }

    @KafkaListener(topics = TOPIC1, groupId = "forErrorHandler", errorHandler = "myErrorHandler")
    public void listenAndThrowExceptionToErrorHandler() {
        log.error("error handler 예제용 예외가 발생했습니다.");
        throw new RuntimeException("error handler 예제용 예외입니다.");
    }
}
