package com.fraudDetectionBankSystem.fraud_detection_system.controller;

import com.fraudDetectionBankSystem.fraud_detection_system.exception.FraudException;
import com.fraudDetectionBankSystem.fraud_detection_system.model.Transaction;
import com.fraudDetectionBankSystem.fraud_detection_system.model.TransactionDTO;
import com.fraudDetectionBankSystem.fraud_detection_system.model.TransactionRequestDTO;
import com.fraudDetectionBankSystem.fraud_detection_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionRequestDTO requestDTO) {
        TransactionDTO transactionDTO = transactionService.processTransaction(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
    }

}
