package com.fraudDetectionBankSystem.fraud_detection_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class FraudCheckResult {
    private boolean isFraud;
    private String reason;

    public FraudCheckResult(boolean isFraud, String reason) {
        this.isFraud = isFraud;
        this.reason = reason;
    }

    public boolean isFraud() {
        return isFraud;
    }

    public void setFraud(boolean fraud) {
        isFraud = fraud;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}