package com.fraudDetectionBankSystem.fraud_detection_system.repository;

import com.fraudDetectionBankSystem.fraud_detection_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    long countByUserIdAndTimestampGreaterThanEqual(Long userId, LocalDateTime timeThreshold);

    List<Transaction> findByUserIdAndTimestampGreaterThanEqual(Long userId, LocalDateTime timeThreshold);

    List<String> findDistinctCountryByUserIdAndTimestampGreaterThanEqual(Long userId, LocalDateTime startTime);
}
