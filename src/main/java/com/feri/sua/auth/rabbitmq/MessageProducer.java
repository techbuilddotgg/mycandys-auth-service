package com.feri.sua.auth.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendToLogQueue(String message) {
        rabbitTemplate.convertAndSend("mycandys-logging-exchange", "mycandys-logging-queue", message);
        System.out.println("Message sent: " + message);
    }
}
