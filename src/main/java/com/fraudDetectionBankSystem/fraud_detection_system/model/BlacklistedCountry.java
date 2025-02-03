package com.fraudDetectionBankSystem.fraud_detection_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blacklisted_countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String countryName;

    public BlacklistedCountry(String countryName) {
        this.countryName = countryName;
    }
}
