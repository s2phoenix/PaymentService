package com.example.PaymentService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class AppConfig {
    // Inject the JWT secret from application.yml
    @Value("${jwt.secret}")
    private String secretKeyConfig;

    // Provide a bean for the SecretKey globally
    @Bean
    public SecretKey secretKey() {
        if (secretKeyConfig == null || secretKeyConfig.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not defined in the configuration!");
        }
        // Decode the secret from Base64 and return it as a SecretKey
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyConfig);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");
    }
}
