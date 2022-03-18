package com.xgk.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author: xugongkai
 * @created: 2022-03-17 11:37
 */
@Slf4j
@Component
public class OrderInfoConsumer {

    @KafkaListener(topicPartitions = {
            @TopicPartition(topic = Constants.Topic.XTEST, partitions = {"0","1"})
    })
    public void consume1(String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partitionId, Acknowledgment ack) {
        log.info(">>>Receive kafka msg from partitionId {}, msg:{}", partitionId, message);
        ack.acknowledge();
    }

    @KafkaListener(topicPartitions = {
            @TopicPartition(topic = Constants.Topic.XTEST, partitions = {"2", "3"})
    })
    public void consume2(String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partitionId, Acknowledgment ack) {
        log.info(">>>Receive kafka msg from partitionId {}, msg:{}", partitionId, message);
        ack.acknowledge();
    }

}
