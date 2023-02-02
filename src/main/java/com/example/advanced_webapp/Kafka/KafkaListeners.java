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
    void listener(String data) throws IOException {
        String[] dataArray = data.split(",");
        String email = dataArray[0].substring(6);
        String link = dataArray[1].substring(5);
        emailService.send(email, link);
    }
}
