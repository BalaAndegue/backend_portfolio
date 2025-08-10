package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PortfolioBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortfolioBackendApplication.class, args);
        /*String plainPassword = "admin123";  // le mot de passe en clair à hacher
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(plainPassword);

        System.out.println("Mot de passe en clair : " + plainPassword);
        System.out.println("Mot de passe haché BCrypt : " + hashedPassword);*/
    }
}