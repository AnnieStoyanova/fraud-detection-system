package com.fraudDetectionBankSystem.fraud_detection_system.controller;

import com.fraudDetectionBankSystem.fraud_detection_system.exception.FraudException;
import com.fraudDetectionBankSystem.fraud_detection_system.model.Transaction;
import com.fraudDetectionBankSystem.fraud_detection_system.model.TransactionDTO;
import com.fraudDetectionBankSystem.fraud_detection_system.model.TransactionRequestDTO;
import com.fraudDetectionBankSystem.fraud_detection_system.service.KafkaProducerService;
import com.fraudDetectionBankSystem.fraud_detection_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final KafkaProducerService kafkaProducerService;

    private static final String TRANSACTION_TOPIC = "transactions";
    public TransactionController(TransactionService transactionService, KafkaProducerService kafkaProducerService) {
        this.transactionService = transactionService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequestDTO requestDTO) {

        kafkaProducerService.sendTransaction(TRANSACTION_TOPIC, requestDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Transaction sent to Kafka");
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

}
