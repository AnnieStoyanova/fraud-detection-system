package com.fraudDetectionBankSystem.fraud_detection_system.service;

import com.fraudDetectionBankSystem.fraud_detection_system.exception.FraudException;
import com.fraudDetectionBankSystem.fraud_detection_system.model.*;
import com.fraudDetectionBankSystem.fraud_detection_system.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
//@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionService.class);


    public TransactionService(TransactionRepository transactionRepository,
                              FraudDetectionService fraudDetectionService) {
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
    }

    public TransactionDTO processTransaction(TransactionRequestDTO requestDTO) {
        Transaction transaction = mapToEntity(requestDTO);

        FraudCheckResult fraudCheckResult = fraudDetectionService.checkFraud(transaction);

        if (fraudCheckResult.isFraud()) {
            log.warn("Transaction detected as FRAUD: User={}, Reason={}",
                    transaction.getUserId(), fraudCheckResult.getReason());
            throw new FraudException("Your transaction was flagged as potentially fraudulent.");
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return the DTO with the transaction info and the false fraudResult
        return mapToDTO(savedTransaction, new FraudCheckResult(false, null));
    }


    private Transaction mapToEntity(TransactionRequestDTO requestDTO) {
        return new Transaction(
                null, // tranId will be auto-generated
                requestDTO.getUserId(),
                requestDTO.getAmount(),
                LocalDateTime.now(),
                requestDTO.getCountry(),
                requestDTO.getLatCoord(),
                requestDTO.getLongCoord()
        );
    }


    private TransactionDTO mapToDTO(Transaction transaction, FraudCheckResult fraudCheckResult) {
        return new TransactionDTO(
                transaction.getTranId(),
                transaction.getAmount(),
                transaction.getTimestamp(),
                transaction.getCountry(),
                fraudCheckResult
        );
    }
}



