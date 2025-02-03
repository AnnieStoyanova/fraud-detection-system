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

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.userId = :userId AND t.timestamp >= :timeThreshold")
    long countTransactionsInLastMinute(@Param("userId") Long userId, @Param("timeThreshold") LocalDateTime timeThreshold);

    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND t.timestamp >= :timeThreshold")
    List<Transaction> findRecentTransactions(@Param("userId") Long userId, @Param("timeThreshold") LocalDateTime timeThreshold);

    @Query("SELECT DISTINCT t.country FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND t.timestamp >= :startTime")
    List<String> findDistinctCountries(@Param("userId") Long userId,
                                       @Param("startTime") LocalDateTime startTime);
}
