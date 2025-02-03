package com.fraudDetectionBankSystem.fraud_detection_system.exception;

import lombok.Getter;

@Getter
public class FraudException extends RuntimeException {
    private String message;

    public FraudException(String message) {
        super(message);
        this.message = message;
    }
}
