package com.fraudDetectionBankSystem.fraud_detection_system.service;

import com.fraudDetectionBankSystem.fraud_detection_system.model.TransactionRequestDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "transactions", groupId = "transaction-consumers", concurrency = "3")
    public void consumeTransaction(String transactionMessage) {
        try {
            TransactionRequestDTO transactionDTO = objectMapper.readValue(transactionMessage, TransactionRequestDTO.class);
            
            transactionService.processTransaction(transactionDTO);
            
            System.out.println("Processed transaction: " + transactionDTO);

        } catch (Exception e) {
            System.err.println("Error processing transaction: " + e.getMessage());
        }
    }
}
