package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionClientApplication {

    private static final String TRANSACTION_API_URL = "http://localhost:8080/api/transactions/add";
    private static final int NUM_THREADS = 10; // Number of concurrent clients
    private static final int NUM_TRANSACTIONS = 10000; // Total transactions to send
    private static final Random random = new Random();
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_TRANSACTIONS; i++) {
            executorService.submit(() -> {
                try {
                    TransactionRequestDTO transactionRequest = generateRandomTransactionRequest();
                    sendTransaction(transactionRequest);
                } catch (Exception e) {
                    System.err.println("Error sending transaction: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Executor interrupted: " + e.getMessage());
        }
    }

    private static TransactionRequestDTO generateRandomTransactionRequest() {
        return new TransactionRequestDTO(
                (long) (random.nextInt(10000) + 1),  // Random User ID
                random.nextDouble() * 10000,        // Random Amount (0 - 10,000)
                getRandomCountry(),
                random.nextDouble() * 180 - 90,     // Random Latitude (-90 to +90)
                random.nextDouble() * 360 - 180     // Random Longitude (-180 to +180)
        );
    }

    private static void sendTransaction(TransactionRequestDTO transactionRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(transactionRequest), headers);

            ResponseEntity<String> response = restTemplate.exchange(TRANSACTION_API_URL, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.ACCEPTED) {
                System.out.println("Sent Transaction: " + transactionRequest);
            } else {
                System.err.println("Failed Transaction: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error sending transaction: " + e.getMessage());
        }
    }

    private static String getRandomCountry() {
        String[] countries = {"USA", "Germany", "Canada", "France", "India", "UK", "Brazil", "Australia"};
        return countries[random.nextInt(countries.length)];
    }
}
