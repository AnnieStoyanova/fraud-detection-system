package com.fraudDetectionBankSystem.fraud_detection_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long tranId;
    private Double amount;
    private LocalDateTime timestamp;
    private String country;
    private FraudCheckResult fraudCheckResult;
}

