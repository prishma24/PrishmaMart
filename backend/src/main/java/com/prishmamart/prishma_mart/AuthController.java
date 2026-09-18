package com.prishmamart.prishma_mart;

import com.prishmamart.prishma_mart.model.User;
import com.prishmamart.prishma_mart.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username already exists"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(
                Map.of("message", "Registration successful")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        User user = userRepository
                .findByUsername(loginUser.getUsername())
                .orElse(null);

        if (user == null ||
            !passwordEncoder.matches(
                    loginUser.getPassword(),
                    user.getPassword())) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid username or password"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "username", user.getUsername()
                )
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        User user = userRepository
                .findByUsernameAndEmail(username, email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username or email is incorrect"));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(
                Map.of("message", "Password reset successful")
        );
    }
}