package com.fraudDetectionBankSystem.fraud_detection_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private Long userId;
    private Double amount;
    private String country;
    private Double latCoord;
    private Double longCoord;
}