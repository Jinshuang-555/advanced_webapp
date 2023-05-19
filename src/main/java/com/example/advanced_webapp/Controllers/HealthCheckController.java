package com.example.advanced_webapp.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaAdmin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;

import java.util.Set;

@RestController
public class HealthCheckController {
    private final JdbcTemplate jdbcTemplate;
    private final KafkaAdmin kafkaAdmin;

    @Autowired
    public HealthCheckController(JdbcTemplate jdbcTemplate, KafkaAdmin kafkaAdmin) {
        this.jdbcTemplate = jdbcTemplate;
        this.kafkaAdmin = kafkaAdmin;
    }

    @GetMapping("/healthz")
    public ResponseEntity<String> livenessProbe() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/ready")
    public ResponseEntity<String> readinessProbe() {
        try {
            // You can use a simple SQL query to check if the database connection is ready.
            // This SQL is compatible with most databases, but you should replace it with an appropriate query for your database.
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);

            String topicToCheck = "sql";
            try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
                ListTopicsResult listTopics = adminClient.listTopics();
                Set<String> topicNames = listTopics.names().get();
                System.out.println("Kafka Topics: "+ topicNames);
                if (!topicNames.contains(topicToCheck)) {
                    return ResponseEntity.status(503).body("Kafka topic does not exist");
                }
            }

            return ResponseEntity.ok("OK");
        } catch (Exception ex) {
            // If an exception occurs, it means either the database or Kafka topic is not ready, so return a non-200 HTTP status.
            return ResponseEntity.status(503).body("Database is not ready");
        }
    }
}
