package com.inn.cafe;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Assuming this is the stored encoded password
        String storedEncodedPassword = "$2a$10$Qllx.mYHGruzobMpaGrDceaPoya5uoNCA67JHuNxzw31qZo0Y9M06";  // Example hash

        // Password entered by user during login
        String rawPassword = "1234";

        // Verify the password
        boolean matches = passwordEncoder.matches(rawPassword, storedEncodedPassword);
        System.out.println("Password matches: " + matches);
    }

}
