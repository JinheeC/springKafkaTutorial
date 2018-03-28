package com.example.springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyRecoveryCallback implements RecoveryCallback {

    @Override
    public Object recover(RetryContext context) {
        int retryCount = context.getRetryCount();
        ConsumerRecord record = (ConsumerRecord) context.getAttribute("record");

        log.info("{} 번째 재시도 했습니다.", retryCount);
        log.info("recovery 로직에서 {}를 처리했습니다.", record.value());
        return record;
    }
}
