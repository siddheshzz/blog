package com.siddhesh.blog.config;

import com.siddhesh.blog.domain.entities.User;
import com.siddhesh.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Seeds a default dev user AFTER Spring context + JPA are ready.
 * Replaces the old approach of writing to the DB inside a @Bean method,
 * which runs too early (no transaction, DB may not be up yet).
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        String email = "user@test.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            User newUser = User.builder()
                    .name("Test User")
                    .email(email)
                    .password(passwordEncoder.encode("password"))
                    .build();
            userRepository.save(newUser);
        }
    }
}
