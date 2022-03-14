package com.xgk.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: xugongkai
 * @created: 2022-03-14 14:43
 */
@Slf4j
@Component
public class MQListener {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = Constants.Queue.ORDER_QUEUE, containerFactory = "orderQueueContainerFactory")
    void orderQueueListener(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException, InterruptedException {
        String str = new String(message.getBody());
        OrderInfo orderInfo = objectMapper.readValue(str, OrderInfo.class);
        log.info("Receive order info from [orderQueue] : {}", orderInfo);
        //channel.basicReject(deliveryTag, false);
        channel.basicAck(deliveryTag, true);
    }

}
