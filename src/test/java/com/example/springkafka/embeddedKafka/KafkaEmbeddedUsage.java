package com.example.springkafka.embeddedKafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DirtiesContext
public class KafkaEmbeddedUsage {

    @Autowired
    private KafkaAdmin admin;

    @Autowired
    private NewTopic topic1;

    @Autowired
    private NewTopic topic2;

    @Test
    public void testAddTopics() throws Exception {
        AdminClient adminClient = AdminClient.create(this.admin.getConfig());
        DescribeTopicsResult topics = adminClient.describeTopics(Arrays.asList("foo", "bar"));
        topics.all().get();
        new DirectFieldAccessor(this.topic1).setPropertyValue("numPartitions", 2);
        new DirectFieldAccessor(this.topic2).setPropertyValue("numPartitions", 3);
        this.admin.initialize();

        topics = adminClient.describeTopics(Arrays.asList("foo", "bar"));
        Map<String, TopicDescription> results = topics.all().get();
        results.forEach((name, td) -> assertThat(td.partitions().size()).isEqualTo(name.equals("foo") ? 2 : 3));
        adminClient.close(10, TimeUnit.SECONDS);
    }

    @Configuration
    public static class Config {

        @Bean
        public KafkaEmbedded kafkaEmbedded() {
            return new KafkaEmbedded(1);
        }

        @Bean
        public KafkaAdmin admin() {
            Map<String, Object> configs = new HashMap<>();
            configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                StringUtils.arrayToCommaDelimitedString(kafkaEmbedded().getBrokerAddresses()));
            return new KafkaAdmin(configs);
        }

        @Bean
        public NewTopic topic1() {
            return new NewTopic("foo", 1, (short) 1);
        }

        @Bean
        public NewTopic topic2() {
            return new NewTopic("bar", 1, (short) 1);
        }

    }
}