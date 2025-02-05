package com.fraudDetectionBankSystem.fraud_detection_system.service;

import com.fraudDetectionBankSystem.fraud_detection_system.model.TransactionRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendTransaction(String topic, TransactionRequestDTO transactionRequestDTO) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(transactionRequestDTO);

            System.out.println("Sending JSON message to Kafka: " + jsonMessage);

            CompletableFuture<Void> future = kafkaTemplate.send(topic, jsonMessage)
                    .thenAccept(result -> System.out.println("Successfully sent: " + jsonMessage))
                    .exceptionally(e -> {
                        System.err.println("Failed to send message: " + e.getMessage());
                        return null;
                    });

            future.join();

        } catch (Exception e) {
            System.err.println("Error serializing transaction: " + e.getMessage());
        }
    }
}


