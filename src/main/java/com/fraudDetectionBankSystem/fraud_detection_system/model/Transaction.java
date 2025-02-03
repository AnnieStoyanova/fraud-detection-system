package com.fraudDetectionBankSystem.fraud_detection_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // TODO: type Identity research
    private Long tranId;

    private Long userId;
    private Double amount;
    private LocalDateTime timestamp;
    private String country;
    private Double latCoord;
    private Double longCoord;


    public Transaction(Long tranId, Long userId, Double amount, LocalDateTime timestamp, String country, Double longCoord, Double latCoord) {
        this.tranId = tranId;
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.country = country;
        this.longCoord = longCoord;
        this.latCoord = latCoord;
    }

    public Long getTranId() {
        return tranId;
    }

    public void setTranId(Long tranId) {
        this.tranId = tranId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatCoord() {
        return latCoord;
    }

    public void setLatCoord(Double latCoord) {
        this.latCoord = latCoord;
    }

    public Double getLongCoord() {
        return longCoord;
    }

    public void setLongCoord(Double longCoord) {
        this.longCoord = longCoord;
    }
}
