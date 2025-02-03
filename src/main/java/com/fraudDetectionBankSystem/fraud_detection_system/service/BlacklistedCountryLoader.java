package com.fraudDetectionBankSystem.fraud_detection_system.service;

import com.fraudDetectionBankSystem.fraud_detection_system.model.BlacklistedCountry;
import com.fraudDetectionBankSystem.fraud_detection_system.repository.BlacklistedCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlacklistedCountryLoader implements CommandLineRunner {

    private final BlacklistedCountryRepository blacklistedCountryRepository;

    public BlacklistedCountryLoader(BlacklistedCountryRepository blacklistedCountryRepository) {
        this.blacklistedCountryRepository = blacklistedCountryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> countries = List.of("North Korea", "Iran", "Syria", "Sudan", "Cuba");

        for (String country : countries) {
            if (!blacklistedCountryRepository.existsByCountryName(country)) {
                blacklistedCountryRepository.save(new BlacklistedCountry(country));
                System.out.println("Inserted blacklisted country: " + country);
            }
        }
    }
}
