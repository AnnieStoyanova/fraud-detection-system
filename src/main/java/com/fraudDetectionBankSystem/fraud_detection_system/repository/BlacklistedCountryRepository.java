package com.fraudDetectionBankSystem.fraud_detection_system.repository;

import com.fraudDetectionBankSystem.fraud_detection_system.model.BlacklistedCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedCountryRepository extends JpaRepository<BlacklistedCountry, Long> {

    boolean existsByCountryName(String country);
}
