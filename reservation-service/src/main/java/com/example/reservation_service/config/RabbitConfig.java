package com.example.reservation_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "reservationExchange";
    public static final String ROUTING_KEY = "reservation.created";
    public static final String QUEUE_NAME = "reservationCreatedQueue";

    // Exchange
    @Bean
    public TopicExchange reservationExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Queue
    @Bean
    public Queue reservationCreatedQueue() {
        return new Queue(QUEUE_NAME, true); 
    }

    // Binding
    @Bean
    public Binding bindingReservationCreated(Queue reservationCreatedQueue, 
                                            TopicExchange reservationExchange) {
        return BindingBuilder.bind(reservationCreatedQueue)
                .to(reservationExchange)
                .with(ROUTING_KEY);
    }
}
