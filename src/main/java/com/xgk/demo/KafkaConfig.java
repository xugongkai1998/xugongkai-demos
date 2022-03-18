package com.xgk.demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.*;

/**
 * @author: xugongkai
 * @created: 2022-03-17 13:46
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String server;

    @Bean
    KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        return new KafkaAdmin(props);
    }

    @Bean
    AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfigurationProperties());
    }

    @Bean
    Object createTopic(AdminClient adminClient) {
        adminClient.deleteTopics(Collections.singletonList(Constants.Topic.XTEST));
        List<NewTopic> topicList = new LinkedList<>();
        topicList.add(new NewTopic(Constants.Topic.XTEST, 4, (short) 1));
        adminClient.createTopics(topicList);
        return new Object();
    }

}
