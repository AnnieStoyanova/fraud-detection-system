package com.fraudDetectionBankSystem.fraud_detection_system.service;

import com.fraudDetectionBankSystem.fraud_detection_system.model.FraudCheckResult;
import com.fraudDetectionBankSystem.fraud_detection_system.model.FraudReason;
import com.fraudDetectionBankSystem.fraud_detection_system.model.Transaction;
import com.fraudDetectionBankSystem.fraud_detection_system.repository.BlacklistedCountryRepository;
import com.fraudDetectionBankSystem.fraud_detection_system.repository.TransactionRepository;
import com.fraudDetectionBankSystem.fraud_detection_system.utils.GeoUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
//@Slf4j
public class FraudDetectionService {

    private final TransactionRepository transactionRepository;
    private final BlacklistedCountryRepository blacklistedCountryRepository;

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionService.class);


    public FraudDetectionService(TransactionRepository transactionRepository, BlacklistedCountryRepository blacklistedCountryRepository) {
        this.transactionRepository = transactionRepository;
        this.blacklistedCountryRepository = blacklistedCountryRepository;
    }


    public FraudCheckResult checkFraud(Transaction transaction) {
        // 1
        if (isTimeFraud(transaction)) {
            log.warn("Fraud detected: Too many transactions for user {}", transaction.getUserId());
            return new FraudCheckResult(true, FraudReason.HIGH_FREQUENCY.toString());
        }
        // 2
        if(isDistanceFraud(transaction)){
            log.warn("Fraud detected: Two transactions in 30 min in more than 300 km distance for user {}",
                    transaction.getUserId());
            return new FraudCheckResult(true, FraudReason.EXTREME_DISTANCE.toString());
        }
        // 3
        if (isBlacklistedCountry(transaction.getCountry())) {
            log.warn("Fraud detected: Blacklisted country - {}", transaction.getCountry());
            return new FraudCheckResult(true, FraudReason.BLACKLISTED_COUNTRY.toString());
        }
        // 4
        if (isMultipleCountryFraud(transaction)) {
            log.warn("Fraud detected: More than 3 countries in 10 min for user {}", transaction.getUserId());
            return new FraudCheckResult(true, FraudReason.MULTIPLE_COUNTRIES.toString());
        }

        return new FraudCheckResult(false, null);
    }


    // Rule 1: More than 10 transactions in 1 minute
    public boolean isTimeFraud(Transaction transaction) {
        LocalDateTime oneMinuteAgo = transaction.getTimestamp().minusMinutes(1);
        return transactionRepository.countTransactionsInLastMinute(transaction.getUserId(), oneMinuteAgo) > 10;
    }


    // Rule 2: Distance Check (>300 km within 30 min)
    public boolean isDistanceFraud(Transaction transaction) {

        LocalDateTime thirtyMinutesAgo = transaction.getTimestamp().minusMinutes(30);
        List<Transaction> recentTransactions = fetchRecentTransactions(transaction, thirtyMinutesAgo);

        for (Transaction pastTransaction : recentTransactions) {
            double distance = GeoUtils.calculateDistance(
                    transaction.getLatCoord(), transaction.getLongCoord(),
                    pastTransaction.getLatCoord(), pastTransaction.getLongCoord()
            );

            if (distance > 300) {
                return true; // FRAUD DETECTED !
            }
        }
        return false; // No fraud detected
    }


    private List<Transaction> fetchRecentTransactions(Transaction transaction, LocalDateTime thirtyMinutesAgo) {
        return transactionRepository.findRecentTransactions(
                transaction.getUserId(), thirtyMinutesAgo
        );
    }


    // Rule 3: BlackListed Country
    public boolean isBlacklistedCountry(String country) {
        return blacklistedCountryRepository.existsByCountryName(country);
    }

    // Rule 4: Transactions in 3 Countries in 10 Minutes
    public boolean isMultipleCountryFraud(Transaction transaction) {
        List<String> countries = transactionRepository.findDistinctCountries
                (transaction.getUserId(), transaction.getTimestamp().minusMinutes(10));
        return countries.size() >= 3;
    }
}
