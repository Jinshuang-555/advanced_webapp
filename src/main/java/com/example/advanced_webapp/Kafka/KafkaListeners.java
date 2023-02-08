package com.example.advanced_webapp.Kafka;

import com.example.advanced_webapp.Email.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaListeners {

    private final EmailService emailService;

    public KafkaListeners(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "registration",groupId = "groupId")
    void listener(Message message) throws IOException {
        System.out.println(message.getEmail());
        System.out.println(message.getLink());
        emailService.send(message.getEmail(), message.getLink());
    }
}
