package tec.jvgualdi.user_service.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tec.jvgualdi.user_service.dto.EmailRequest;

@Component
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public EmailPublisher(RabbitTemplate rabbitTemplate,
                          @Value("${spring.rabbitmq.queue}") String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName      = queueName;
    }

    public void publish(EmailRequest emailRequest) {
        rabbitTemplate.convertAndSend(queueName, emailRequest);
    }
}
