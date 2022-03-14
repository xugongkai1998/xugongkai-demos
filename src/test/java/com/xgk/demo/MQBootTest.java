package com.xgk.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: xugongkai
 * @created: 2022-03-14 14:49
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQBootTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerEndpointRegistry endpointRegistry;

    @Before
    public void before() {
        endpointRegistry.stop();
    }

    @Test
    public void testPublishMsgToOrderQueue() throws JsonProcessingException {
        for (int i = 0; i < 50; i++) {
            OrderInfo orderInfo = OrderInfo.randomOrderInfo();
            String s = objectMapper.writeValueAsString(orderInfo);
            rabbitTemplate.convertAndSend(Constants.Exchange.DEFAULT_EXCHANGE, Constants.RouterKey.DEFAULT_ROUTER_KEY, orderInfo);
            log.info("Publish MQ success:{}", s);
        }
    }

}
