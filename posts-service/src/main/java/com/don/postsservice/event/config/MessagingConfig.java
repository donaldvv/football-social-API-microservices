package com.don.postsservice.event.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Donald Veizi
 *
 * Same config as users-microservice
 */
@Configuration
public class MessagingConfig {

    public static final String ROUTING_KEY_USERS_WRITE = "routing.users.write";
    public static final String USER_WRITE_QUEUE_NAME = "queue.users.write";

    @Bean
    public Queue userQueue() {
        return new Queue(USER_WRITE_QUEUE_NAME, false);
    }

    @Bean
    public Queue queueAll() {
        return new Queue("queue.all", false);
    }// as long as i put a generic key in the binding of this queue, it will be able to listen to all the produced events with KEY that match the generic key

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("users.write");
    }

    @Bean
    public Binding userWriteBinding(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(userQueue)
                .to(exchange)
                .with(ROUTING_KEY_USERS_WRITE);
    }

    @Bean
    public Binding bindingAll(Queue queueAll, TopicExchange exchange) {
        return BindingBuilder
                .bind(queueAll)
                .to(exchange)
                .with("routing.*"); // this queue will be able to get all messages sent with Routing Key routing.Whatever. So if i sent message in Producer, with key: routing.users.write, it will be listened by this queue as well
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
