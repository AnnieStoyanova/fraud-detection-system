package com.fraudDetectionBankSystem.fraud_detection_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FraudCheckResult {
    private boolean isFraud;
    private String reason;

    public boolean isFraud() {
        return isFraud;
    }
}