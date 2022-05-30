package com.don.usersservice.event;

import com.don.usersservice.event.dto.Message;
import com.don.usersservice.event.dto.user.UserMessage;

/**
 * @author Donald Veizi
 */
public interface UserEventProducer {

    /**
     * Will send User data and action that the consumer must do with that data.
     * Ex: when user is created, the posts-microservice (consumer)
     * will need to store the user id and some important data into its own DB as well.
     * @param message wrapper class that will contain UserMessage and the action that the consumer will have to do
     */
    void sendUserWrite(Message<UserMessage> message);
}
