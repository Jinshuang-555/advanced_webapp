package com.example.advanced_webapp.Kafka;

import com.example.advanced_webapp.Kafka.Message.EmailMessage;
import com.example.advanced_webapp.Kafka.Message.TaskMessage;
import com.example.advanced_webapp.Tables.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    private final KafkaTemplate<String, TaskMessage> taskKafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, EmailMessage> kafkaTemplate, KafkaTemplate<String, TaskMessage> taskKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.taskKafkaTemplate = taskKafkaTemplate;
    }

    public void sendMessage(String topic, EmailMessage person) {
        ListenableFuture<SendResult<String, EmailMessage>> future = kafkaTemplate.send(topic, person);
        future.addCallback(new ListenableFutureCallback<SendResult<String, EmailMessage>>() {
            @Override
            public void onSuccess(SendResult<String, EmailMessage> result) {
                System.out.println("Sent message=[" + person + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.err.println("Unable to send message=[" + person + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendSqlMessage(String topic, TaskMessage task) {
        ListenableFuture<SendResult<String, TaskMessage>> future = taskKafkaTemplate.send(topic, task);
        future.addCallback(new ListenableFutureCallback<SendResult<String, TaskMessage>>() {
            @Override
            public void onSuccess(SendResult<String, TaskMessage> result) {
                System.out.println("Sent message=[" + task + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                System.out.println(task.getTask());
                System.out.println(task.getSummary());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.err.println("Unable to send message=[" + task + "] due to : " + ex.getMessage());
            }
        });
    }
}

