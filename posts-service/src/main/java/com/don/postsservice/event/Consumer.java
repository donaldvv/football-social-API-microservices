package com.don.postsservice.event;

import com.don.postsservice.event.config.MessagingConfig;
import com.don.postsservice.event.dto.Message;
import com.don.postsservice.event.dto.user.UserMessage;
import com.don.postsservice.service.UserExtService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Donald Veizi
 */
@Component
@RequiredArgsConstructor
public class Consumer {

    private final UserExtService userExtService;

    @RabbitListener(queues = MessagingConfig.USER_WRITE_QUEUE_NAME)
    public void receiveFromUser(Message<UserMessage> message) {
        userExtService.processUserEvent(message);
    }

    // demo only. since the queue that this uses, is binded with a generic key, all producers that
    // have a KEY that matcher with the generic one, will also produce the message to this queue as well
    // Ex. producer method in users-ws: sendUserWrite() uses key: routing.users.write. this queue will also recieve that msg, bcs it is binded with key routing.*
    @RabbitListener(queues = "queue.all")
    public void receiveFromAll(Message<UserMessage> message) {

    }
}
