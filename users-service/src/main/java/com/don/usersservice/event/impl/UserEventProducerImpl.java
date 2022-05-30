package com.don.usersservice.event.impl;

import com.don.usersservice.event.UserEventProducer;
import com.don.usersservice.event.dto.Message;
import com.don.usersservice.event.dto.user.UserMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.don.usersservice.event.config.MessagingConfig.ROUTING_KEY_USERS_WRITE;

/**
 * @author Donald Veizi
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducerImpl implements UserEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    public void sendUserWrite(Message<UserMessage> message) {
        rabbitTemplate.convertAndSend(topicExchange.getName(), ROUTING_KEY_USERS_WRITE, message);
        log.info("Message sent successfully to posts-microservice");
    }

}
