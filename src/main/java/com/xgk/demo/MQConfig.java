package com.xgk.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xugongkai
 * @created: 2022-03-14 14:22
 */
@Slf4j
@Configuration
public class MQConfig {

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean(name = "orderQueueContainerFactory")
    SimpleRabbitListenerContainerFactory orderQueueContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory c = new SimpleRabbitListenerContainerFactory();
        c.setRecoveryInterval(10000L);
        c.setMaxConcurrentConsumers(1);
        c.setConcurrentConsumers(1);
        c.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factoryConfigurer.configure(c, connectionFactory);
        return c;
    }

    @Bean
    Object orderQueueBinding(ConnectionFactory connectionFactory) throws IOException {
        Connection conn = connectionFactory.createConnection();
        Channel channel = conn.createChannel(false);
        initDeathLetterQueue(channel);
        initOrderQueue(channel);
        conn.close();
        return new Object();
    }

    private void initOrderQueue(Channel channel) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", Constants.Exchange.DEFAULT_EXCHANGE);
        map.put("x-dead-letter-routing-key", Constants.RouterKey.DEATH_ROUTER_KEY);
        map.put("x-message-ttl", 1000 * 10);
        map.put("x-max-length", 100);
        channel.queueDeclare(Constants.Queue.ORDER_QUEUE, true, false, false, map);
        channel.queueBind(Constants.Queue.ORDER_QUEUE, Constants.Exchange.DEFAULT_EXCHANGE, Constants.RouterKey.DEFAULT_ROUTER_KEY);
        log.info("order queue init success");
    }

    private void initDeathLetterQueue(Channel channel) throws IOException {
        channel.queueDeclare(Constants.Queue.DEATH_QUEUE, true, false, false, null);
        channel.queueBind(Constants.Queue.DEATH_QUEUE, Constants.Exchange.DEFAULT_EXCHANGE, Constants.RouterKey.DEATH_ROUTER_KEY);
        log.info("death letter init success");
    }





}
